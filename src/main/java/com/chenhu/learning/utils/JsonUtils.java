package com.chenhu.learning.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈虎
 */
@Component
public class JsonUtils {
    private static Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        JsonUtils.gson = gson;
    }

    public static <T> List<T> parseToList(List<Map> list, Class<T> clazz) {
        return list.stream().map(e -> gson.fromJson(gson.toJson(e), clazz)).collect(Collectors.toList());
    }

    public static <T> List<T> parseArrayToList(Object object, Class<T> clazz) {
        return JSON.parseArray(JSON.toJSONString(object), clazz);
    }
}
