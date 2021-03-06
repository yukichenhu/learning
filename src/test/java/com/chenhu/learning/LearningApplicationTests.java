package com.chenhu.learning;

import com.chenhu.learning.bo.UserBO;
import com.chenhu.learning.entity.User;
import com.chenhu.learning.query.QueryWrapper;
import com.chenhu.learning.repository.UserRepository;
import com.chenhu.learning.utils.JsonUtils;
import com.chenhu.learning.utils.QueryUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;

@SpringBootTest
class LearningApplicationTests {

    @Resource
    private UserRepository userRepository;
    @Resource
    private QueryUtils queryUtils;
    @Test
    void contextLoads() {
        String sql="select u.name,u.email,(select count(*) from t_position where position_id=u.position_id) " +
                "from t_user u where name='zhangsan'";
        System.out.println(queryUtils.getCountSql(sql));
        sql="select t.name from (select name from t_user group by name) t";
        System.out.println(queryUtils.getCountSql(sql));
    }

    @Test
    void testJsonTrans(){
        List<Map> originList=new ArrayList<>();
        Map map=new HashMap<String,Object>(2){{
            put("name","zhangsan");
            put("email","zhangsan@qq.com");
        }};
        originList.add(map);
        List<UserBO> results2= JsonUtils.parseArrayToList(originList,UserBO.class);
        System.out.println(results2.toString());
    }

    @Test
    void save(){
        User user=User.builder().name("lisi").email("lisi@163.com").contactPhone("17558435869").build();
        userRepository.save(user);
    }

    @Test
    void testExampleMatcher(){
        User req=User.builder().name("zhangsan").contactPhone("6678").build();
        ExampleMatcher matcher=ExampleMatcher.matchingAll()
                .withMatcher("contactPhone",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<User> example=Example.of(req,matcher);
        System.out.println(userRepository.findAll(example));
    }


    @Test
    @SuppressWarnings("unchecked")
    void testSpecificationExecutor(){
        User req=User.builder().name("s").contactPhone("7").build();
        Specification<User> sp= (root, query, criteriaBuilder) -> {
            Predicate p=criteriaBuilder.conjunction();
            List<Expression<Boolean>> ps=p.getExpressions();
            if(!ObjectUtils.isEmpty(req.getName())){
                ps.add(criteriaBuilder.like(root.get("name"),"%"+req.getName()+"%"));
            }
            if(!ObjectUtils.isEmpty(req.getContactPhone())){
                ps.add(criteriaBuilder.like(root.get("contactPhone"),"%"+req.getContactPhone()+"%"));
            }
            query.select(root.get("name")).where(p).groupBy(root.get("id"));
            return query.getRestriction();
        };
        List<User> users=userRepository.findAll(sp);
        System.out.println(users);
    }

    @Test
    void testMyQuery(){
        String mainSql="select u.name,u.email,p.position_name from t_user u join t_position p on u.position_id=p.position_id";
        String name="zhangsan";
        String email="qq.com";
        List<Integer> ids= Arrays.asList(1,2);
        QueryWrapper queryWrapper=new QueryWrapper()
                .eq(!ObjectUtils.isEmpty(name),"u.name",name)
                .like(!ObjectUtils.isEmpty(email),"u.email",email)
                .in(!ObjectUtils.isEmpty(ids),"u.id",ids)
                .groupBy("u.name")
                .orderByDesc("u.id");
        List<UserBO> users=queryUtils.queryList(mainSql, queryWrapper,UserBO.class);
        System.out.println(users);
    }

    @Test
    void testMyPageQuery(){
        String mainSql="select u.name,u.email,p.position_name from t_user u join t_position p on u.position_id=p.position_id";
        QueryWrapper queryWrapper=new QueryWrapper()
                .eq("u.name","zhangsan")
                .like("u.email","qq.com")
                .orderByDesc("u.id");
        Pageable page= PageRequest.of(0,10);
        Page<UserBO> users=queryUtils.queryPage(mainSql, queryWrapper,UserBO.class,page);
        System.out.println(users.getTotalElements());
        System.out.println(users.getContent());
    }
}
