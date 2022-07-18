package com.chenhu.learning.security;

import com.chenhu.learning.annotation.Auth;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * @author 陈虎
 * @date 2022-06-24 11:45
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (object instanceof MethodInvocation) {
            MethodInvocation mi = (MethodInvocation)object;
            Auth auth=mi.getMethod().getAnnotation(Auth.class);
            if(!ObjectUtils.isEmpty(auth)){
                String[] permissions=auth.permissions();
                return SecurityConfig.createList(permissions);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
