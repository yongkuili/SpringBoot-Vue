package com.yan.config.security;

import com.yan.config.filter.CustomFilter;
import com.yan.config.filter.CustomUrlDecisionManager;
import com.yan.config.jwt.JwtAuthenticationFilter;
import com.yan.config.jwt.RestAuthorizationEntryPoint;
import com.yan.config.jwt.RestfulAccessDeniedHandler;
import com.yan.pojo.Admin;
import com.yan.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author LiYongkui
 * @title: SecurityConfig
 * @description: Security 配置类
 * @date 2021-05-29 0299:29
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint; // 未登录 token 失效时自定义处理结果

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler; // 无权访问时自定义处理结果
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;
    @Autowired
    private CustomFilter customFilter;
    /**
     * 1.重写 UserDetailsService，用我们自己写的业务逻辑
     *
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("----------SecurityConfig 1----------->【UserDetailsService】");
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if (admin != null) {
                admin.setRoles(adminService.getRolesByAdminId(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("用户名或密码不正确！");
        };
    }

    /**
     * 2.让 Security 走我们重写的 UserDetailsService ，通过 getAdminByUserName 获取用户名
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("----------SecurityConfig 2----------->【configure-AuthenticationManagerBuilder】");
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 3、密码加解密对象
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("----------SecurityConfig 3----------->【passwordEncoder】");
        return new BCryptPasswordEncoder();
    }

    /**
     * 4、SpringSecurity 配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("----------SecurityConfig 4----------->【configure-HttpSecurity】");
        // 使用 JWT , 不需要 csrf
        http.csrf()
                .disable()
                // 基于 token,不需要 session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //允许登录
                /*.antMatchers("/login", "/logout")
                .permitAll()*/
                //除了上面所有请求要求认证
                .anyRequest()
                .authenticated()
                //动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and()
                .headers()
                 .cacheControl();
                 //添加jwt，登录授权过滤器
                     http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
                     //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    /**
     * 5、JWT 登录授权过滤器
     * @return
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        System.out.println("----------SecurityConfig 4----------->【jwtAuthenticationFilter】");
        return new JwtAuthenticationFilter();
    }

    /**
     * 6、放行路径（不走拦截链）
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        System.out.println("----------SecurityConfig 6----------->【configure-WebSecurity】");
        web.ignoring().antMatchers(
                "/websocket/**",
                "/**.html",
                "/login/**",
                "/register/**",
                "/logout/**",
                "/css/**",
                "/js/**",
                "/img/**",
                "/fonts/**",
                "favicon.ico",
                "/doc.html",                    // 放行 swagger 资源
                "/webjars/**",                  // 放行 swagger 资源
                "/swagger-resources/**",        // 放行 swagger 资源
                "/v2/api-docs/**",              // 放行 swagger 资源
                "/captcha",      // 验证码接口
                "/ws/**"
        );
    }
}
