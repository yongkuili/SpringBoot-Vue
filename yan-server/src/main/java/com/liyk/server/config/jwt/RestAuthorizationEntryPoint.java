package com.liyk.server.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyk.server.utils.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LiYongkui
 * @title: restAuthorizationEntryPoint
 * @description: 当未登录或者 token 失效时访问接口时，自定义返回结果
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 * @date 2021-05-29 02911:27
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        System.out.println("----------RestAuthorizationEntryPoint----------->【commenc】");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("未登录或者token失效，请先登录!");
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }


}
