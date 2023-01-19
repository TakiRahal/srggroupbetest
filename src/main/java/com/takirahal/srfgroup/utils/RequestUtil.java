package com.takirahal.srfgroup.utils;

import com.takirahal.srfgroup.constants.SrfGroupConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
public class RequestUtil {

    private static MessageSource messageSource;

    @Autowired
    public RequestUtil(MessageSource messageSource) {
        RequestUtil.messageSource = messageSource;
    }

    public static String messageTranslate(String message) {
        Locale locale = Locale.forLanguageTag(getHeaderAttribute(SrfGroupConstants.LANG_KEY));
        return messageSource.getMessage(message, null, locale);
    }

    public static String getHeaderAttribute(String attributeName) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        String attrValue = "";
        if (attributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            attrValue = request.getHeader(attributeName);
        }
        return attrValue.isEmpty() ? "" : attrValue;
    }

}
