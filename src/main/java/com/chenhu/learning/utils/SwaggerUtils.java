package com.chenhu.learning.utils;

import com.chenhu.learning.exception.MyException;
import com.google.gson.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-20 14:33
 */
public class SwaggerUtils {

    private static final Gson gson=new GsonBuilder().create();

    private SwaggerUtils(){

    }
    public static OpenAPI readFromUrl(String url){
        OpenAPI openApi;
        try{
            openApi=new OpenAPIV3Parser().read(url);
        }catch (Exception e){
            throw new MyException("swagger解析失败，请检查url");
        }
        return openApi;
    }

    //合并请求参数
    @SafeVarargs
    public static List<Parameter> mergeParameters(Map<String,Object> schemas, List<Parameter>... parameters){
        String body="$body";
        Map<String,Parameter> parameterMap=new HashMap<>();
        for (List<Parameter> parameter : parameters) {
            parameter.forEach(e->{
                String key= "body".equals(e.getIn())?body:e.getName();
                assert key!=null;
                if(parameterMap.containsKey(key)&&body.equals(key)){
                    //存在则需要处理
                    String ref1=parameterMap.get(key).getSchema().get$ref();
                    String ref2=e.getSchema().get$ref();
                    if(!ref1.equals(ref2)){
                        String key1=ref1.substring(ref1.lastIndexOf("/")+1);
                        String key2=ref2.substring(ref2.lastIndexOf("/")+1);
                        String keyNew=key1+"Merge"+key2;
                        e.getSchema().set$ref("#/definitions/"+keyNew);
                        mergeRef(schemas,key1,key2);
                    }
                }
                parameterMap.put(key,e);
            });
        }
        return new ArrayList<>(parameterMap.values());
    }

    private static void mergeRef(Map<String,Object> schemas,String key1,String key2){
        JsonObject schema1=gson.fromJson(gson.toJson(schemas.get(key1)),JsonObject.class);
        JsonObject schema2=gson.fromJson(gson.toJson(schemas.get(key2)),JsonObject.class);
        schemas.put(key1+"Merge"+key2,merge(schemas,schema1,schema2));
    }

    private static JsonElement merge(Map<String,Object> schemas,JsonObject... elements){
        //结果
        String ref="$ref";
        JsonObject result=new JsonObject();
        for (JsonObject jb : elements) {
            for (Map.Entry<String, JsonElement> elementEntry : jb.entrySet()) {
                String key=elementEntry.getKey();
                JsonElement value=elementEntry.getValue();
                if(!result.has(key)||!jb.isJsonObject()||(!result.get(key).isJsonObject()&&!ref.equals(key))){
                    //不存在或者不是jsonObject
                    result.add(key,value);
                }else{
                    //已存在且为jsonObject做merge
                    if(ref.equals(key)){
                        String ref1=result.get(key).getAsString();
                        String ref2=value.getAsString();
                        if(!ref1.equals(ref2)){
                            String key1=ref1.substring(ref1.lastIndexOf("/")+1);
                            String key2=ref2.substring(ref2.lastIndexOf("/")+1);
                            String keyNew=key1+"Merge"+key2;
                            result.add(key,new JsonPrimitive("#/definitions/"+keyNew));
                            mergeRef(schemas,key1,key2);
                        }else {
                            result.add(key,value);
                        }
                    }else{
                        result.add(key,merge(schemas,result.getAsJsonObject(key),value.getAsJsonObject()));
                    }
                }
            }
        }
        return result;
    }

    //合并返回参数
    @SafeVarargs
    public static Object mergeResponses(Map<String,Object> schemas, Map<String, Object>... responses){
        List<JsonObject> objects=new ArrayList<>();
        for (Map<String, Object> resp : responses) {
            objects.add(gson.fromJson(gson.toJson(resp),JsonObject.class));
        }
        return merge(schemas,objects.toArray(new JsonObject[0]));
    }

    //根据修改后的返回参数处理合并后的结果
    public static JsonObject convertToMergeResult(Map<String,Object> schemas,Map<String,Object> response,JsonObject result){
        JsonObject respObject=gson.fromJson(gson.toJson(response),JsonObject.class);
        JsonObject success=respObject
                .getAsJsonObject("200")
                .getAsJsonObject("content")
                .getAsJsonObject("*/*")
                .getAsJsonObject("schema");
        String responseEntity=success.get("$ref").getAsString();
        responseEntity=responseEntity.substring(responseEntity.lastIndexOf("/")+1);
        JsonObject schema=gson.fromJson(gson.toJson(schemas.get(responseEntity)),JsonObject.class);
        return convert(schemas,schema,result);
    }

    private static JsonObject convert(Map<String,Object> schemas,JsonObject schema,JsonObject result){
        JsonObject filtered=new JsonObject();
        JsonObject properties=schema.getAsJsonObject("properties");
        for (Map.Entry<String, JsonElement> entry : result.entrySet()) {
            if(!properties.has(entry.getKey())){
                continue;
            }
            //判断是数组还是对象
            JsonElement element= entry.getValue();
            if(element.isJsonObject()){
                //json对象
                String ref=properties.getAsJsonObject(entry.getKey()).get("$ref").getAsString();
                if(!ObjectUtils.isEmpty(ref)){
                    ref=ref.substring(ref.lastIndexOf("/")+1);
                    JsonObject childSchema=gson.fromJson(gson.toJson(schemas.get(ref)),JsonObject.class);
                    filtered.add(entry.getKey(), convert(schemas,childSchema,element.getAsJsonObject()));
                }else{
                    filtered.add(entry.getKey(), element);
                }
                continue;
            }
            if(element.isJsonArray()){
                //json 数组
                JsonArray filteredArray=new JsonArray();
                String ref=properties.getAsJsonObject(entry.getKey()).getAsJsonObject("items").get("$ref").getAsString();
                if(!ObjectUtils.isEmpty(ref)){
                    ref=ref.substring(ref.lastIndexOf("/")+1);
                    JsonObject childSchema=gson.fromJson(gson.toJson(schemas.get(ref)),JsonObject.class);
                    for (JsonElement jsonElement : element.getAsJsonArray()) {
                        filteredArray.add(convert(schemas,childSchema,jsonElement.getAsJsonObject()));
                    }
                    filtered.add(entry.getKey(), filteredArray);
                }else{
                    filtered.add(entry.getKey(), element);
                }
                continue;
            }
            filtered.add(entry.getKey(), element);
        }
        return filtered;
    }
}
