package com.chenhu.learning.utils;

import com.chenhu.learning.annotation.Auth;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 陈虎
 * @date 2022-06-24 17:45
 */
@Component
public class AnnotationUtil {
    @Resource
    private ApplicationContext applicationContext;

    public List<String> getAnnotationRequestUrls(){
        List<String> servletUrls=new ArrayList<>();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RestController.class);
        for (Object value : beans.values()) {
            Class<?> beanClass= AopUtils.getTargetClass(value);
            //先获取类上面@requestmapping注解
            String path1="";
            RequestMapping requestMapping= beanClass.getDeclaredAnnotation(RequestMapping.class);
            if(!ObjectUtils.isEmpty(requestMapping)){
                String[] paths=requestMapping.value();
                if(!ObjectUtils.isEmpty(paths)){
                    path1=paths[0];
                }
            }
            //获取带有指定注解的method
            Method[] methods=beanClass.getDeclaredMethods();
            for (Method method : methods) {
                Auth auth=method.getDeclaredAnnotation(Auth.class);
                if(ObjectUtils.isEmpty(auth)){
                    continue;
                }
                String path2=getAnnotationUrl(method);
                if(!ObjectUtils.isEmpty(path2)){
                    servletUrls.add(concat(path1,path2));
                }
            }
        }
        return servletUrls;
    }

    private String getAnnotationUrl(Method m){
        String path="";
        RequestMapping annotation1=m.getAnnotation(RequestMapping.class);
        if(!ObjectUtils.isEmpty(annotation1)){
            String[] paths=annotation1.value();
            path=ObjectUtils.isEmpty(paths)?"":paths[0];
            return path;
        }
        PostMapping annotation2=m.getAnnotation(PostMapping.class);
        if(!ObjectUtils.isEmpty(annotation2)){
            String[] paths=annotation2.value();
            path=ObjectUtils.isEmpty(paths)?"":paths[0];
            return path;
        }
        GetMapping annotation3=m.getAnnotation(GetMapping.class);
        if(!ObjectUtils.isEmpty(annotation3)){
            String[] paths=annotation3.value();
            path=ObjectUtils.isEmpty(paths)?"":paths[0];
            return path;
        }
        return path;
    }

    private String concat(String path1,String path2){
        StringBuilder sb=new StringBuilder();
        if(!ObjectUtils.isEmpty(path1)){
            path1=path1.startsWith("/")?path1:"/"+path1;
            sb.append(path1);
        }
        path2=path2.startsWith("/")?path2:"/"+path2;
        sb.append(path2);
        return sb.toString();
    }
}
