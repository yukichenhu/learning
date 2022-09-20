package com.chenhu.learning.controller;

import com.chenhu.learning.database.DatabaseInfo;
import com.chenhu.learning.database.DbClient;
import com.chenhu.learning.database.QuerySql;
import com.chenhu.learning.entity.DatasourceEntity;
import com.chenhu.learning.entity.SqlApiEntity;
import com.chenhu.learning.exception.MyException;
import com.chenhu.learning.repository.DatasourceRepository;
import com.chenhu.learning.repository.SqlApiRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-20 10:37
 */
@RestController
@RequestMapping("sql")
@AllArgsConstructor
public class ApiController {

    private final DatasourceRepository datasourceRepository;
    private final SqlApiRepository sqlApiRepository;

    @PostMapping("/{id}")
    private Object executeSql(@RequestBody Map<String,Object> params,@PathVariable("id") Integer id){
        SqlApiEntity sqlApi=sqlApiRepository.findById(id).orElse(null);
        if(ObjectUtils.isEmpty(sqlApi)){
            throw new MyException("sql api not exist");
        }
        QuerySql querySql=new QuerySql();
        querySql.setSql(sqlApi.getQuerySql());
        DatasourceEntity datasource=datasourceRepository.findById(sqlApi.getDatasourceId()).orElse(null);
        if(ObjectUtils.isEmpty(datasource)){
            throw new MyException("datasource not exist");
        }
        DatabaseInfo databaseInfo=DatabaseInfo.builder().build();
        BeanUtils.copyProperties(datasource,databaseInfo);
        DbClient client=new DbClient();
        return client.executeQuery(databaseInfo,querySql,params);
    }
}
