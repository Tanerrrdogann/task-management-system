package com.gorevyonetimi.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("userEmail") != null;

        String uri = request.getRequestURI();
        if (uri.startsWith("/login") || uri.startsWith("/css") || uri.startsWith("/webjars") || uri.equals("/")) {
            return true;
        }

        if (!isLoggedIn) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
