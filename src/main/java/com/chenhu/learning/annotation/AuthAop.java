package com.chenhu.learning.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 陈虎
 * @date 2022-07-04 17:19
 */
@Aspect
@Component
@Slf4j
public class AuthAop {

    @Pointcut("@annotation(com.chenhu.learning.annotation.Auth)")
    public void authPointCut(){
    }

    @Before(value = "authPointCut()")
    public void accessDecision(JoinPoint point) {
        Signature signature=point.getSignature();
        if(signature instanceof MethodSignature){
            Method method=((MethodSignature) signature).getMethod();
            Auth auth=method.getAnnotation(Auth.class);
            if(!ObjectUtils.isEmpty(auth)){
                boolean access=true;
                String[] permissions=auth.permissions();
                Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
                List<String> authorities=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                for (String permission : permissions) {
                    access=access&&authorities.contains(permission);
                }
                if(!access){
                    throw new RuntimeException("无权限访问！");
                }
            }
        }
    }
}
