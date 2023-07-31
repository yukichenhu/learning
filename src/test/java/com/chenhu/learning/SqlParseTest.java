package com.chenhu.learning;

/**
 * @author chenhu
 * @description sql解析，封装测试 针对jsqlParser
 * @date 2023-03-08 9:29
 */
public class SqlParseTest {

//    @Test
//    public void testExtractTableNames() throws JSQLParserException {
//        String pgSql="select sr.service_id,s.name,'服务发布审批' as review_type,sr.created_date as apply_time,sr.approve_status,sr.review_time,sr.approve_user,sr.remark from t_service_review sr join t_service s on sr.service_id=s.id and s.latest=1 where sr.apply_user='chenhu' union all select sr.service_id,s.name,'服务订阅审批' as review_type,sr.apply_time,sr.approve_status,sr.review_time,sr.review_user as approve_user,sr.remark from t_subscribe_review sr join t_service s on sr.service_id=s.id and s.latest=1 where sr.apply_user='chenhu' order by apply_time desc";
//        Statement s=CCJSqlParserUtil.parse(pgSql);
//        TablesNamesFinder finder=new TablesNamesFinder();
//        System.out.println(finder.getTableList(s));
//    }
}
