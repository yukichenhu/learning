package com.chenhu.learning.security;

import com.chenhu.learning.annotation.Auth;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author 陈虎
 * @date 2022-06-24 11:45
 */
@Component
public class MyMethodInvocationSecurityMetadataSource extends AbstractMethodSecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
        Auth auth=method.getAnnotation(Auth.class);
        if(!ObjectUtils.isEmpty(auth)){
            String[] permissions=auth.permissions();
            return SecurityConfig.createList(permissions);
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
