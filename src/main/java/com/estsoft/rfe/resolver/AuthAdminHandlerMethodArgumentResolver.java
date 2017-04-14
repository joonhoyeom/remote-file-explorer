package com.estsoft.rfe.resolver;

import com.estsoft.rfe.annotation.AuthAdmin;
import com.estsoft.rfe.vo.AdminVo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by joonho on 2017-04-12.
 */
public class AuthAdminHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        AuthAdmin authAdmin = methodParameter.getParameterAnnotation(AuthAdmin.class);
        if (authAdmin == null) {
            return false;
        }
        if (methodParameter.getParameterType().equals(AdminVo.class) == false) {
            return false;
        }
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        if (supportsParameter(methodParameter) == false)
            return WebArgumentResolver.UNRESOLVED;

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession();
        if (session == null)
            return WebArgumentResolver.UNRESOLVED;

        return session.getAttribute("authAdmin");
    }
}
