package com.chenhu.learning.database;

import com.chenhu.learning.utils.MapUtils;
import lombok.SneakyThrows;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-19 16:21
 */
public class DbClient {


    public List<Map<String,Object>> executeQuery(DatabaseInfo db, QuerySql queryInfo,Map<String,Object> params){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            //动态加载驱动文件
            URL url=new URL(db.getDriverFileUrl());
            URLClassLoader ucl=new URLClassLoader(new URL[]{url});
            Driver driver=(Driver) Class.forName(db.getDriverClass(),true,ucl).newInstance();
            //代理driver
            DriverManager.registerDriver(new DriverProxy(driver));
            //创建连接
            conn=DriverManager.getConnection(db.getUrl(),db.getUsername(),db.getPassword());
            //预处理sql并映射参数
            String pattern="\\$\\{([a-zA-Z_$][a-zA-Z_\\d$]*)}";
            Pattern p=Pattern.compile(pattern);
            Matcher matcher=p.matcher(queryInfo.getSql());
            StringBuffer sb=new StringBuffer();
            //预编译的值
            List<Object> values=new ArrayList<>();
            while (matcher.find()){
                matcher.appendReplacement(sb,"?");
                String paramName= matcher.group(1);
                values.add(params.getOrDefault(paramName,""));
            }
            matcher.appendTail(sb);
            //参数值
            ps= conn.prepareStatement(sb.toString());
            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i+1,values.get(i));
            }
            //处理结果并返回
            rs=ps.executeQuery();
            ResultSetMetaData metaData=ps.getMetaData();
            return convertRs2List(rs,metaData);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            closeConnection(conn,ps,rs);
        }
        return null;
    }

    private void closeConnection(Connection conn,PreparedStatement ps,ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @SneakyThrows
    private List<Map<String,Object>> convertRs2List(ResultSet rs,ResultSetMetaData metaData){
        int count= metaData.getColumnCount();
        List<Map<String,Object>> results=new ArrayList<>();
        while(rs.next()){
            Map<String,Object> result=new HashMap<>();
            for (int i = 1; i <= count; i++) {
                result.put(metaData.getColumnName(i).toLowerCase(),rs.getObject(i));
            }
            results.add(MapUtils.convertStrKeyToCamel(result));
        }
        return results;
    }
}
