package com.chenhu.learning.security;

import com.chenhu.learning.annotation.Auth;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 陈虎
 * @date 2022-07-04 16:17
 */
public class MyAuthVoter implements AccessDecisionVoter<MethodInvocation> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, MethodInvocation object, Collection<ConfigAttribute> attributes) {
        Auth auth=object.getMethod().getAnnotation(Auth.class);
        if(!ObjectUtils.isEmpty(auth)){
            boolean access=true;
            String[] permissions=auth.permissions();
            List<String> authorities=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            for (String permission : permissions) {
                access=access&&authorities.contains(permission);
            }
            return access?1:-1;
        }else{
            return 0;
        }
    }
}
