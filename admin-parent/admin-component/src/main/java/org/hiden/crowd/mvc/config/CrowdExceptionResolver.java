package org.hiden.crowd.mvc.config;

import com.google.gson.Gson;
import org.hiden.crowd.constant.CrowdConstant;
import org.hiden.crowd.exception.AccessForbiddenException;
import org.hiden.crowd.exception.LoginAcctAlreadyInUseException;
import org.hiden.crowd.exception.LoginFailedException;
import org.hiden.crowd.util.CrowdUtil;
import org.hiden.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @ControllerAdvice表示当前类是一个基于注解的异常处理类
@ControllerAdvice
public class CrowdExceptionResolver {
    //@ExceptionHandler将具体异常类型和一个方法关联起来
//    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveCommonException(String view, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean judgeRequest = CrowdUtil.judgeRequestType(request);
        if (judgeRequest) {
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            response.getWriter().write(json);
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(view);
        return modelAndView;
    }

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return resolveCommonException("admin-login", exception, request, response);
    }

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return resolveCommonException("admin-add", exception, request, response);
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return resolveCommonException("system-error", exception, request, response);
    }
}
