package com.yan.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.utils.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LiYongkui
 * @title: RestfulAccessDeniedHandler
 * @description: 当访问接口没有权限时，自定义返回结果
 * AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 * @date 2021-05-29 02911:29
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("----------RestfulAccessDeniedHandler----------->【handle】");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("权限不足，请联系管理员！");
        bean.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
