package com.chenhu.learning.utils;

import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author 陈虎
 * @date 2022-06-22 14:58
 */
public class SshUtils {

    public static void testCmd(){
        String username="陈虎";
        String pwd="123456";
        String host="192.168.30.103";
        //测试执行指令
        Session session=JschUtil.getSession(host,22,username,pwd);
        String conf=JschUtil.exec(session,"type d:\\\\documents\\kong.yml", null);
        System.out.println(conf);
        JschUtil.close(session);
    }
    public static void testUpdateFile() {
        String username="redapp";
        String pwd="redapp123456";
        String host="192.168.0.184";
        //测试执行指令
        String conf= FileUtils.read("D:/documents/kong.yml");
        //测试修改文件
        ByteArrayInputStream inputStream=new ByteArrayInputStream(conf.getBytes(StandardCharsets.UTF_8));
        Sftp sftp=JschUtil.createSftp(host,22,username,pwd);
        sftp.upload("/home/redapp/chenhu-test/","test.yml",inputStream);
        sftp.close();
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
