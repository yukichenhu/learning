package com.chenhu.learning.query;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author 陈虎
 * @since 2022-06-01 11:13
 */
@Data
@SuperBuilder
public class QueryFilter {
    private String filterName;
    private Object filterValue;
    private Object filterValueOther;
    private String operator;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryFilter that = (QueryFilter) o;
        return this.filterName.equals(that.getFilterName())&&this.filterValue.equals(that.filterValue)&&this.operator.equals(that.getOperator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterName, filterValue,operator);
    }

    public String buildSql(Set<Object> params){
        StringBuilder sb=new StringBuilder();
        switch (operator){
            case "=":
                sb.append(filterName).append("= ? ");
                params.add(filterValue);
                break;
            case "like":
                sb.append(filterName).append(" like ? ");
                params.add("%"+filterValue+"%");
                break;
            case "not like":
                sb.append(filterName).append(" not like ? ");
                params.add("%"+filterValue+"%");
                break;
            case "like left":
                sb.append(filterName).append(" like ? ");
                params.add(filterValue+"%");
                break;
            case "like right":
                sb.append(filterName).append(" like ? ");
                params.add("%"+filterValue);
                break;
            case "!=":
                sb.append(filterName).append(" != ? ");
                params.add(filterValue);
                break;
            case ">":
                sb.append(filterName).append(" > ? ");
                params.add(filterValue);
                break;
            case ">=":
                sb.append(filterName).append(" >= ? ");
                params.add(filterValue);
                break;
            case "<":
                sb.append(filterName).append(" < ? ");
                params.add(filterValue);
                break;
            case "<=":
                sb.append(filterName).append(" <= ? ");
                params.add(filterValue);
                break;
            case "between":
                sb.append(filterName).append(" between ? and ? ");
                params.add(filterValue);
                params.add(filterValueOther);
                break;
            case "not between":
                sb.append(filterName).append(" not between ? and ? ");
                params.add(filterValue);
                params.add(filterValueOther);
                break;
            case "is null":
                sb.append(filterName).append(" is null ");
                break;
            case "is not null":
                sb.append(filterName).append(" is not null ");
                break;
            case "in":
                sb.append(filterName).append(" in (");
                Collection inList=(Collection) filterValue;
                for (Object o : inList) {
                    sb.append("?,");
                    params.add(o);
                }
                sb.deleteCharAt(sb.length()-1).append(") ");
                break;
            case "not in":
                sb.append(filterName).append(" not in (");
                Collection notList=(Collection) filterValue;
                for (Object o : notList) {
                    sb.append("?,");
                    params.add(o);
                }
                sb.deleteCharAt(sb.length()-1).append(") ");
            default:
        }
        return sb.toString();
    }
}
