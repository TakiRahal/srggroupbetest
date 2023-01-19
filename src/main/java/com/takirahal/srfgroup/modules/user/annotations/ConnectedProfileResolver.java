package com.takirahal.srfgroup.modules.user.annotations;

import com.takirahal.srfgroup.security.UserPrincipal;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class ConnectedProfileResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Annotation[] annotations = methodParameter.getParameterAnnotations();
        boolean isFound = Arrays.stream(annotations).anyMatch(it -> it.annotationType().equals(ConnectedProfile.class));

        if (methodParameter.getParameterType().equals(UserPrincipal.class) && isFound) {
            return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        return WebArgumentResolver.UNRESOLVED;
    }
}
