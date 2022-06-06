package com.chenhu.learning.query;

import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author 陈虎
 * @since 2022-06-01 11:01
 */
public class QueryWrapper{
    private final Set<QueryFilter> filters=new LinkedHashSet<>();
    private final Set<String> orderColumns=new LinkedHashSet<>();
    private final Set<String> groupColumns=new LinkedHashSet<>();

    private final Set<Object> params=new LinkedHashSet<>();

    public Set<Object> getParams(){
        return this.params;
    }

    public QueryWrapper eq(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("=").build());
        return this;
    }

    public QueryWrapper eq(boolean condition,String name,Object value){
        if(condition){
            return eq(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper ne(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("!=").build());
        return this;
    }

    public QueryWrapper ne(boolean condition,String name,Object value){
        if(condition){
            return ne(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper gt(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator(">").build());
        return this;
    }

    public QueryWrapper gt(boolean condition,String name,Object value){
        if(condition){
            return gt(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper ge(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator(">=").build());
        return this;
    }

    public QueryWrapper ge(boolean condition,String name,Object value){
        if(condition){
            return ge(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper lt(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("<").build());
        return this;
    }

    public QueryWrapper lt(boolean condition,String name,Object value){
        if(condition){
            return lt(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper le(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("<=").build());
        return this;
    }

    public QueryWrapper le(boolean condition,String name,Object value){
        if(condition){
            return le(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper between(String name,Object value,Object value2){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).filterValueOther(value2).operator("between").build());
        return this;
    }

    public QueryWrapper between(boolean condition,String name,Object value,Object value2){
        if(condition){
            return between(name,value,value2);
        }else{
            return this;
        }
    }

    public QueryWrapper notBetween(String name,Object value,Object value2){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).filterValueOther(value2).operator("not between").build());
        return this;
    }

    public QueryWrapper notBetween(boolean condition,String name,Object value,Object value2){
        if(condition){
            return notBetween(name,value,value2);
        }else{
            return this;
        }
    }

    public QueryWrapper isNull(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("is null").build());
        return this;
    }

    public QueryWrapper isNull(boolean condition,String name,Object value){
        if(condition){
            return isNull(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper isNotNull(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("is not null").build());
        return this;
    }

    public QueryWrapper isNotNull(boolean condition,String name,Object value){
        if(condition){
            return isNotNull(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper like(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("like").build());
        return this;
    }

    public QueryWrapper like(boolean condition,String name,Object value){
        if(condition){
            return like(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper notLike(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("not like").build());
        return this;
    }

    public QueryWrapper notLike(boolean condition,String name,Object value){
        if(condition){
            return notLike(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper likeLeft(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("like left").build());
        return this;
    }

    public QueryWrapper likeLeft(boolean condition,String name,Object value){
        if(condition){
            return likeLeft(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper likeRight(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("like right").build());
        return this;
    }

    public QueryWrapper likeRight(boolean condition,String name,Object value){
        if(condition){
            return likeRight(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper in(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("in").build());
        return this;
    }

    public QueryWrapper in(boolean condition,String name,Object value){
        if(condition){
            return in(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper notIn(String name,Object value){
        filters.add(QueryFilter.builder().filterName(name).filterValue(value).operator("not in").build());
        return this;
    }

    public QueryWrapper notIn(boolean condition,String name,Object value){
        if(condition){
            return notIn(name,value);
        }else{
            return this;
        }
    }

    public QueryWrapper orderBy(String column,String sort){
        String desc = "desc";
        if(desc.equalsIgnoreCase(sort)){
            return orderByDesc(column);
        }
        String asc = "asc";
        if(asc.equalsIgnoreCase(sort)){
            return orderByAsc(column);
        }
        return this;
    }

    public QueryWrapper orderByAsc(String... column){
        for (String s : column) {
            orderColumns.add(s+" asc");
        }
        return this;
    }

    public QueryWrapper groupBy(String... column){
        groupColumns.addAll(Arrays.asList(column));
        return this;
    }

    public QueryWrapper orderByDesc(String... column){
        for (String s : column) {
            orderColumns.add(s+" desc");
        }
        return this;
    }

    public String generateSql(){
        //select sql
        StringBuilder sql=new StringBuilder(generateCountSql());
        //compose group sql
        if(!ObjectUtils.isEmpty(groupColumns)){
            sql.append("group by ");
            for (String groupColumn : groupColumns) {
                sql.append(groupColumn).append(",");
            }
            sql.deleteCharAt(sql.length()-1).append(" ");
        }
        //compose order sql
        if(!ObjectUtils.isEmpty(orderColumns)){
            sql.append("order by ");
            for (String orderColumn : orderColumns) {
                sql.append(orderColumn).append(",");
            }
            sql.deleteCharAt(sql.length()-1);
        }
        return sql.toString();
    }

    public String generateCountSql(){
        boolean isWhere=true;
        StringBuilder sql=new StringBuilder(" ");
        for (QueryFilter filter : filters) {
            if(isWhere){
                sql.append("where ").append(filter.buildSql(params));
                isWhere=false;
            }else{
                sql.append("and ").append(filter.buildSql(params));
            }
        }
        return sql.toString();
    }
}
