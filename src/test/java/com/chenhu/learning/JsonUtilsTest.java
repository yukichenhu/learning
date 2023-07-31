package com.chenhu.learning;

import com.chenhu.learning.utils.JsonUtils;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-21 17:52
 */
public class JsonUtilsTest {

    @Test
    public void testMerge1(){
        Map<String,Object> map1= ImmutableMap.of("name","name1","age",1,"info",ImmutableMap.of("address","address1"));
        Map<String,Object> map2= ImmutableMap.of("name","name2","sex","male","info",ImmutableMap.of("position","position1"));
        Gson gson= new GsonBuilder().create();
        Object result= JsonUtils.mergeJson(gson.toJson(map1),gson.toJson(map2));
        System.out.println(gson.toJson(result));
    }

    @Test
    public void testMerge2(){
        Map<String,Object> map1= ImmutableMap.of("name","name1","age",1,"info",ImmutableMap.of("address","address1"));
        Map<String,Object> map2= ImmutableMap.of("name","name2","sex","male","info",ImmutableMap.of("position","position1"));
        Map<String,Object> map3= ImmutableMap.of("name", Arrays.asList("name1","name2"),"info",ImmutableMap.of("address","address2"));
        Map<String,Object> map4= ImmutableMap.of("info",ImmutableMap.of("friends",Arrays.asList("f1","f2"),"position",Arrays.asList("position1","position2")));
        Gson gson= new GsonBuilder().create();
        Object result= JsonUtils.mergeJson(gson.toJson(map1),gson.toJson(map2), gson.toJson(map3), gson.toJson(map4));
        System.out.println(gson.toJson(result));
    }

    private String formatUrl(String url) {
        if (ObjectUtils.isEmpty(url)) {
            return "";
        }
        url = url.trim();
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        while (url.endsWith("/")) {
            if (url.length() == 1) {
                break;
            }
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    private String getEndpoint(String proxyUrl, String path) {
        String result = formatUrl(proxyUrl) + formatUrl(path);
        result = result.replace("//", "/");
        return result.toLowerCase();
    }

    @Test
    public void testGetEndpoint(){
        System.out.println(getEndpoint("test/","apiTest"));
        System.out.println(getEndpoint("/test","/apiTest"));
        System.out.println(getEndpoint("/","/"));
    }
}
