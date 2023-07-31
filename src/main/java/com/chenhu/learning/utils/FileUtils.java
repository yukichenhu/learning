package com.chenhu.learning.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author 陈虎
 * @date 2022-06-20 9:07
 */
@Slf4j
public class FileUtils {

    private FileUtils(){

    }

    @SneakyThrows
    public static String readText(String filePath){
        char[] readChar=new char[1024];
        FileReader fr=new FileReader(filePath);
        StringBuilder sb=new StringBuilder();
        int len;
        while ((len=fr.read(readChar)) >0){
            sb.append(readChar,0,len);
        }
        fr.close();
        return sb.toString();
    }

    public static String read(String path){
        cn.hutool.core.io.file.FileReader fr=new cn.hutool.core.io.file.FileReader(path);
        return fr.readString();
    }

    @SneakyThrows
    public static void write(String path,String content){
        File file=new File(path);
        if(!file.exists()){
            if(!file.createNewFile()){
                log.error("文件不存在！创建失败");
            }
        }
        FileWriter writer=new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    @SneakyThrows
    public static void main(String[] args) {
        String filePath="kafka-conf/kafka-jaas.conf";
        System.out.println(read(filePath));
    }
}
