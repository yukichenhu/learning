package com.chenhu.learning.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-09 10:11
 */
public class MapUtils {
    public MapUtils(){

    }

    public static <V> Map<String,V> convertStrKeyToCamel(Map<String,V> map){
        Map<String,V> resultMap=new HashMap<>();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            String key= entry.getKey();
            String convertedKey=convertToCamel(key);
            resultMap.put(convertedKey, entry.getValue());
        }
        return resultMap;
    }

    private static String convertToCamel(String key){
        Pattern p=Pattern.compile("_(\\w)");
        StringBuffer sb=new StringBuffer();
        Matcher m=p.matcher(key);
        while(m.find()){
            m.appendReplacement(sb,m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String,String> map= new HashMap<>();
        map.put("first_name","chen");
        map.put("second_name","hu");
        map.put("job_name","java");
        map.put("age","27");
        System.out.println(convertStrKeyToCamel(map));
    }
}
