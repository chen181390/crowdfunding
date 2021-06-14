package org.hiden.crowd.mvc.interceptor;

import org.hiden.crowd.constant.CrowdConstant;
import org.hiden.crowd.entity.Admin;
import org.hiden.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        if (admin == null) {
            throw new AccessForbiddenException(CrowdConstant.Message_ACCESS_FORBIDDEN);
        }
        return true;
    }
}
