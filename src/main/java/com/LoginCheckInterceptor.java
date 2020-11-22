package com;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(false);
        if(session!=null){
            User user = (User)session.getAttribute("user");
            // Login 되어있는지 체크
            if(user!=null)
                return true;
        }
        response.sendRedirect(request.getContextPath()+"/error");
        return false;
    }
}
