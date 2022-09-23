package com.chenhu.learning.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈虎
 */
@Component
public class JsonUtils {
    private static final Gson gson=new GsonBuilder().create();

    public static <T> List<T> parseToList(List<Map> list, Class<T> clazz) {
        return list.stream().map(e -> gson.fromJson(gson.toJson(e), clazz)).collect(Collectors.toList());
    }

    public static <T> List<T> parseArrayToList(Object object, Class<T> clazz) {
        return JSON.parseArray(JSON.toJSONString(object), clazz);
    }

    /**
     * 合并两个json
     * @param jsonStrings 待合并的json
     * @return 合并后的jsonObject
     */
    public static Object mergeJson(String... jsonStrings){
        //聚合结果
        JsonObject[] elements=new JsonObject[jsonStrings.length];
        for (int i = 0; i < jsonStrings.length; i++) {
            try{
                elements[i]=gson.fromJson(jsonStrings[i],JsonObject.class);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return merge(elements);
    }

    private static JsonElement merge(JsonObject... elements){
        //结果
        JsonObject result=new JsonObject();
        for (JsonObject jb : elements) {
            for (Map.Entry<String, JsonElement> elementEntry : jb.entrySet()) {
                String key=elementEntry.getKey();
                JsonElement value=elementEntry.getValue();
                if(!result.has(key)||!jb.isJsonObject()||!result.get(key).isJsonObject()){
                    //不存在或者不是jsonObject
                    result.add(key,value);
                }else{
                    //已存在且为jsonObject做merge
                    result.add(key,merge(result.getAsJsonObject(key),value.getAsJsonObject()));
                }
            }
        }
        return result;
    }
}
