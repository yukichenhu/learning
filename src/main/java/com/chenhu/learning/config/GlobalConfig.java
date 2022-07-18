package com.chenhu.learning.config;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈虎
 * @description
 * @date 2022-07-08 15:11
 */
public class GlobalConfig implements Conf{

    private GlobalConfig(){

    }

    private static GlobalConfig globalConfig;
    private Map<String,String> properties;

    public static GlobalConfig createInstance(){
        if(globalConfig==null){
            globalConfig=new GlobalConfig();
        }
        return globalConfig;
    }

    @Override
    public void refresh() {
        properties=new HashMap<>(readProperties());
    }

    @Override
    public String getWord(String key) {
        if(ObjectUtils.isEmpty(properties)){
            properties=readProperties();
        }
        return properties.get(key);
    }

    private Map<String,String> readProperties(){
        //读取配置 可从文件或者数据库
        return new HashMap<>();
    }
}
