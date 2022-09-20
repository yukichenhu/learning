package com.chenhu.learning;

import com.chenhu.learning.database.DatabaseInfo;
import com.chenhu.learning.database.DbClient;
import com.chenhu.learning.database.QuerySql;
import com.chenhu.learning.entity.Animal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈虎
 * @date 2022-06-15 8:34
 */
public class HutoolTest {

    @Test
    public void testClone() throws JsonProcessingException {
        Animal a=new Animal();
        a.setName(null);
        Animal.Detail detail=new Animal.Detail();
        detail.setAge(2);
        a.setDetail(detail);
        System.out.println(new ObjectMapper().writeValueAsString(a));
        //System.out.println(new GsonBuilder().create().toJson(a));
    }

    @SneakyThrows
    @Test
    public void test() {
        //数据库信息
        DatabaseInfo db=DatabaseInfo
                .builder()
                .driverFileUrl("http://192.168.0.136:9001/learning/mysql-connector-java-8.0.29.jar")
                .driverClass("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://192.168.30.103:3306/test?rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai")
                .username("root")
                .password("mysql@2022")
                .build();
        //查询信息
        QuerySql querySql=new QuerySql();
        querySql.setSql("select email,name,contact_phone from t_user where id= ${id} and name=${name}");
        //查询参数
        Map<String,Object> param=new HashMap<>(2);
        param.put("id",1);
        param.put("name","zhangsan");
        //查询
        DbClient client=new DbClient();
        List<Map<String,Object>> results=client.executeQuery(db,querySql,param);
        assert "[{name=zhangsan, contactPhone=15905196678, email=zhangsan@qq.com}]".equals(results.toString());
    }


    @SneakyThrows
    @Test
    public void test02() {
        //数据库信息
        DatabaseInfo db=DatabaseInfo
                .builder()
                .driverFileUrl("http://192.168.0.136:9001/learning/postgresql-42.3.6.jar")
                .driverClass("org.postgresql.Driver")
                .url("jdbc:postgresql://192.168.0.186:5432/hmesh_dev")
                .username("pgadmin")
                .password("123456")
                .build();
        //查询信息
        QuerySql querySql=new QuerySql();
        querySql.setSql("select * from t_service where path=${path}");
        //查询参数
        Map<String,Object> param=new HashMap<>(1);
        param.put("path","/hmesh");
        //查询
        DbClient client=new DbClient();
        List<Map<String,Object>> results=client.executeQuery(db,querySql,param);
        System.out.println(results);
    }
}
