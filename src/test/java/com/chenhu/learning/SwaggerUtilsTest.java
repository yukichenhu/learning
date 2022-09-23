package com.chenhu.learning;

import com.chenhu.learning.utils.SwaggerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-20 14:36
 */
public class SwaggerUtilsTest {

    @Test
    public void testReadFromUrl(){
        String url="http://192.168.0.136:8080/hmesh-app/api/v1/v3/api-docs";
        OpenAPI openAPI=SwaggerUtils.readFromUrl(url);
        System.out.println(openAPI.getInfo());
        System.out.println(openAPI.getPaths());
    }

    @Test
    public void testMergeParameters1(){
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        Map<String,Object> schemas=new HashMap<>();
        String parameter1="[{\"name\":\"name\",\"in\":\"query\",\"description\":\"搜索名称\",\"type\":\"string\",\"format\":null,\"required\":false,\"deprecated\":null,\"schema\":null},{\"name\":\"entryId\",\"in\":\"query\",\"description\":\"服务分类id\",\"type\":\"string\",\"format\":null,\"required\":false,\"deprecated\":null,\"schema\":null},{\"name\":\"pageNum\",\"in\":\"query\",\"description\":\"页码\",\"type\":\"integer\",\"format\":\"int32\",\"required\":false,\"deprecated\":null,\"schema\":null},{\"name\":\"pageSize\",\"in\":\"query\",\"description\":\"每页数量\",\"type\":\"integer\",\"format\":\"int32\",\"required\":false,\"deprecated\":null,\"schema\":null}]";
        String parameter2="[{\"name\":\"url\",\"in\":\"query\",\"description\":\"swagger url\",\"type\":\"string\",\"format\":null,\"required\":true,\"deprecated\":null,\"schema\":null}]";
        List<Parameter> list1=gson.fromJson(parameter1,new TypeReference<List<Parameter>>(){}.getType());
        List<Parameter> list2=gson.fromJson(parameter2,new TypeReference<List<Parameter>>(){}.getType());
        List<Parameter> merged=SwaggerUtils.mergeParameters(schemas,list1,list2);
        System.out.println(gson.toJson(merged));
    }

    @Test
    public void testMergeParameters2(){
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String schemaStr="{\"ServiceSaveBO\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"apis\":{\"type\":\"array\",\"exampleSetFlag\":false,\"items\":{\"$ref\":\"#/components/schemas/ApiVO\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"specVersion\":\"V30\"},\"serviceName\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"entryId\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"proxyUrl\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"des\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"}},\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"HmeshId\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"version\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"}},\"exampleSetFlag\":false,\"specVersion\":\"V30\"}}";
        Map<String,Object> schemas=gson.fromJson(schemaStr,new TypeReference<Map<String,Object>>(){}.getType());
        String parameter1="[{\"name\":null,\"in\":\"body\",\"description\":null,\"type\":null,\"format\":null,\"required\":true,\"deprecated\":false,\"schema\":{\"$ref\":\"#/definitions/HmeshId\"}}]";
        String parameter2="[{\"name\":null,\"in\":\"body\",\"description\":null,\"type\":null,\"format\":null,\"required\":true,\"deprecated\":false,\"schema\":{\"$ref\":\"#/definitions/ServiceSaveBO\"}}]";
        List<Parameter> list1=gson.fromJson(parameter1,new TypeReference<List<Parameter>>(){}.getType());
        List<Parameter> list2=gson.fromJson(parameter2,new TypeReference<List<Parameter>>(){}.getType());
        List<Parameter> merged=SwaggerUtils.mergeParameters(schemas,list1,list2);
        System.out.println(gson.toJson(merged));
        System.out.println(gson.toJson(schemas));
    }

    @Test
    public void testMergeResponse(){
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String schemaStr="{\"RestResponseListEntryVO\":{\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"integer\",\"format\":\"int32\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"data\":{\"type\":\"array\",\"exampleSetFlag\":false,\"items\":{\"$ref\":\"#/definitions/EntryVO\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"specVersion\":\"V30\"},\"message\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"success\":{\"type\":\"boolean\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"}},\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"RestResponseEntry\":{\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"integer\",\"format\":\"int32\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"data\":{\"$ref\":\"#/definitions/Entry\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"message\":{\"type\":\"string\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"success\":{\"type\":\"boolean\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"}},\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"EntryVO\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\",\"description\":\"分类id\"},\"name\":{\"type\":\"string\",\"description\":\"分类名称\"},\"parentId\":{\"type\":\"string\",\"description\":\"上级分类id\"},\"comment\":{\"type\":\"string\",\"description\":\"分类描述\"},\"children\":{\"type\":\"array\",\"description\":\"子分类列表\",\"items\":{\"$ref\":\"#/definitions/EntryVO\"}}}},\"Entry\":{\"required\":[\"name\",\"parentId\"],\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"string\"},\"createdDate\":{\"type\":\"string\",\"format\":\"date-time\"},\"lastModifiedDate\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"description\":\"分类名称\"},\"parentId\":{\"type\":\"string\",\"description\":\"上级分类id 根目录为0\"},\"domain\":{\"type\":\"string\",\"description\":\"分类所属域 分为api，lambda，service，app\"},\"comment\":{\"type\":\"string\",\"description\":\"分类描述\"}},\"description\":\"分类实体\"}}";
        Map<String,Object> schemas=gson.fromJson(schemaStr,new TypeReference<Map<String,Object>>(){}.getType());
        String response1="{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/definitions/RestResponseListEntryVO\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"exampleSetFlag\":false}}}}";
        String response2="{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/definitions/RestResponseEntry\",\"exampleSetFlag\":false,\"specVersion\":\"V30\"},\"exampleSetFlag\":false}}}}";
        Map<String,Object> list1=gson.fromJson(response1,new TypeReference<Map<String,Object>>(){}.getType());
        Map<String,Object> list2=gson.fromJson(response2,new TypeReference<Map<String,Object>>(){}.getType());
        System.out.println(gson.toJson(SwaggerUtils.mergeResponses(schemas,list1,list2)));
        System.out.println(gson.toJson(schemas));
    }

    @Test
    public void testConvertResponse(){
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String schemaStr="{\"RestResponsePageService\":{\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"integer\",\"format\":\"int32\"},\"data\":{\"$ref\":\"#/components/schemas/PageService\"}}},\"PageService\":{\"type\":\"object\",\"properties\":{\"currentPage\":{\"type\":\"integer\",\"format\":\"int32\"},\"pageSize\":{\"type\":\"integer\",\"format\":\"int32\"},\"list\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Service\"}}}},\"Service\":{\"type\":\"object\",\"properties\":{\"path\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"}}}}";
        Map<String,Object> schemas=gson.fromJson(schemaStr,new TypeReference<Map<String,Object>>(){}.getType());
        String resultStr="{\"code\":200,\"data\":{\"total\":10,\"currentPage\":0,\"pageSize\":20,\"list\":[{\"path\":\"proxyioio\",\"name\":\"测试服务6\",\"status\":0,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"f38637e3-4884-460a-adea-31d57a579ef1\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-19 09:25:00\",\"lastModifiedDate\":1663550700383},{\"path\":\"ssss\",\"name\":\"服务测试8\",\"status\":0,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"7c57d4d2-fd7c-437b-ae71-b0b6d2876198\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-19 09:26:16\",\"lastModifiedDate\":1663550776236},{\"path\":\"fffff\",\"name\":\"服务测试7\",\"status\":0,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"c6af2caa-76ca-45c6-8a78-9a126f912f4f\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-19 09:27:25\",\"lastModifiedDate\":1663550845451},{\"path\":\"ssssproxy\",\"name\":\"服务测试10\",\"status\":0,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"0fac6665-4264-40ce-a560-eef8fa4cbac4\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-19 09:36:18\",\"lastModifiedDate\":1663551378061},{\"path\":\"dffff\",\"name\":\"aaaa\",\"status\":1,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"891f59b1-e240-4f56-97bf-3b6452471168\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-15 16:05:23\",\"lastModifiedDate\":1663229123916},{\"path\":\"/hmesh\",\"name\":\"测试服务1.1\",\"status\":0,\"createUser\":null,\"latest\":1,\"des\":\"just for update\",\"id\":\"5c599f16-128d-4572-bc97-f0fedfc642f5\",\"version\":\"1662455186530\",\"createdDate\":\"2022-09-06 16:26:27\",\"lastModifiedDate\":1662453279588},{\"path\":\"proxyurlllll\",\"name\":\"servicemmm\",\"status\":1,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"b91b09a8-46f7-4e7d-8bbb-43c178643ab0\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-16 15:52:50\",\"lastModifiedDate\":1663314770747},{\"path\":\"aaa333\",\"name\":\"ss\",\"status\":1,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"9554d1e0-9b8f-4a3b-9100-b7d2c037f05a\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-16 18:13:49\",\"lastModifiedDate\":1663323229942},{\"path\":\"proxyuiui\",\"name\":\"sss\",\"status\":1,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"10df52a2-7f1e-41b5-ae02-2aadc68e0a4a\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-19 09:23:02\",\"lastModifiedDate\":1663550582600},{\"path\":\"URLproxy\",\"name\":\"服务测试9\",\"status\":1,\"createUser\":null,\"latest\":1,\"des\":null,\"id\":\"91819abf-b9ec-49dd-9ddc-3d5ac0fe8756\",\"version\":\"version.draft\",\"createdDate\":\"2022-09-19 09:28:02\",\"lastModifiedDate\":1663550882757}]},\"message\":\"success\",\"success\":true}";
        JsonObject result=gson.fromJson(resultStr,JsonObject.class);
        String responseStr="{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/RestResponsePageService\"}}}}}";
        Map<String,Object> responses=gson.fromJson(responseStr,new TypeReference<Map<String,Object>>(){}.getType());
        System.out.println(gson.toJson(SwaggerUtils.convertToMergeResult(schemas,responses,result)));
    }

    @Test
    public void testFindDefinitions(){
        String str="{\"RestResponsePageService\":{\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"integer\",\"format\":\"int32\"},\"data\":{\"$ref\":\"#/components/schemas/PageService\"}}},\"PageService\":{\"type\":\"object\",\"properties\":{\"currentPage\":{\"type\":\"integer\",\"format\":\"int32\"},\"pageSize\":{\"type\":\"integer\",\"format\":\"int32\"},\"list\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Service\"}}}},\"Service\":{\"type\":\"object\",\"properties\":{\"path\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"}}}}";
        Pattern p=Pattern.compile("#/components/schemas/(\\w+)");
        Matcher m=p.matcher(str);
        while (m.find()){
            System.out.println(m.group(1));
        }

    }
}
