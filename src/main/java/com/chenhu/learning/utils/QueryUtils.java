package com.chenhu.learning.utils;

import com.chenhu.learning.query.QueryWrapper;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author 陈虎
 * @since 2022-06-01-10:29
 */
@Component
public class QueryUtils {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public <T> List<T> queryList(String querySql,Class<T> clazz){
        Query query=entityManager.createNativeQuery(querySql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return JsonUtils.parseArrayToList(query.getResultList(),clazz);
    }

    @Transactional(readOnly = true)
    public <T> List<T> queryList(String querySql, QueryWrapper queryWrapper,Class<T> clazz){
        String sql;
        String where="where";
        String wrapperSql=queryWrapper.generateSql();
        if(querySql.contains(where)){
            wrapperSql=wrapperSql.replace("where","and");
        }
        sql=querySql+wrapperSql;

        Query query=entityManager.createNativeQuery(sql);
        //预编译参数
        int startIndex=1;
        for (Object param : queryWrapper.getParams()) {
            query.setParameter(startIndex++,param);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return JsonUtils.parseArrayToList(query.getResultList(),clazz);
    }

    @Transactional(readOnly = true)
    public <T> Page<T> queryPage(String querySql,String countSql, QueryWrapper queryWrapper, Class<T> clazz, Pageable req){
        String selectSql;
        String handledCountSql;
        String where="where";
        String wrapperSql=queryWrapper.generateSql();
        if(querySql.contains(where)){
            wrapperSql=wrapperSql.replace("where","and");
        }
        handledCountSql=countSql+queryWrapper.generateCountSql();
        selectSql=querySql+wrapperSql;
        Query query=entityManager.createNativeQuery(selectSql);
        Query countQuery=entityManager.createNativeQuery(handledCountSql);
        //预编译参数
        int startIndex=1;
        for (Object param : queryWrapper.getParams()) {
            countQuery.setParameter(startIndex,param);
            query.setParameter(startIndex++,param);
        }
        query.setFirstResult(req.getPageSize()*req.getPageNumber());
        query.setMaxResults(req.getPageSize());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        int totalNum=Integer.parseInt(countQuery.getResultList().get(0).toString());
        return new PageImpl<>(JsonUtils.parseArrayToList(query.getResultList(),clazz),req,totalNum);
    }

    @Transactional(readOnly = true)
    public <T> Page<T> queryPage(String querySql, QueryWrapper queryWrapper, Class<T> clazz, Pageable req){
        //自动构建countSql
        String countSql=getCountSql(querySql);
        return queryPage(querySql,countSql,queryWrapper,clazz,req);
    }

    public String getCountSql(String querySql){
        int start=0;
        int end=0;
        int selectNum=0;
        String select="select";
        String from="from";
        String[] words=querySql.split(" ");
        for (int i = 0; i < words.length; i++) {
            if(words[i].toLowerCase().contains(select)){
                if(selectNum==0){
                    start=i;
                }
                selectNum++;
            }
            if(words[i].toLowerCase().contains(from)){
                selectNum--;
                if(selectNum==0){
                    end=i;
                    break;
                }
            }
        }

        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < words.length; i++){
            if(i==start){
                sb.append(words[i]).append(" count(*) as num ");
                continue;
            }
            if(i>start&&i<end){
                continue;
            }
            sb.append(words[i]).append(" ");
        }

        return sb.toString();
    }
}
