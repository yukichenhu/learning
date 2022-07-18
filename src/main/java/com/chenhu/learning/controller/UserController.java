package com.chenhu.learning.controller;

import cn.hutool.core.map.MapUtil;
import com.chenhu.learning.annotation.Auth;
import com.chenhu.learning.bo.UserBO;
import com.chenhu.learning.config.MyPageRequest;
import com.chenhu.learning.entity.Post;
import com.chenhu.learning.entity.User;
import com.chenhu.learning.query.QueryWrapper;
import com.chenhu.learning.repository.PostRepository;
import com.chenhu.learning.repository.UserRepository;
import com.chenhu.learning.utils.PropertyUtils;
import com.chenhu.learning.utils.QueryUtils;
import com.chenhu.learning.utils.SshUtils;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author 陈虎
 */
@RestController
@RequestMapping(value = "test")
public class UserController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private QueryUtils queryUtils;

    @Resource
    private PostRepository postRepository;

    @Auth()
    @PostMapping("addUser")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @PostMapping("addPost")
    public Post addUser(@RequestBody Post post) {
        if (!ObjectUtils.isEmpty(post.getPostId())) {
            Post origin = postRepository.findById(post.getPostId()).orElse(null);
            if (!ObjectUtils.isEmpty(origin)) {
                PropertyUtils.copyNotNullProperty(post, origin);
                return postRepository.save(origin);
            }
        }
        return postRepository.save(post);
    }

    @PostMapping("findPage")
    public Page<UserBO> findPage(MyPageRequest pageRequest) {
        String mainSql = "select u.name,u.email,p.position_name from t_user u join t_position p on u.position_id=p.position_id";
        QueryWrapper queryWrapper = new QueryWrapper()
                .eq("u.name", "zhangsan")
                .like("u.email", "qq.com")
                .orderByDesc("u.id");
        return queryUtils.queryPage(mainSql, queryWrapper, UserBO.class, pageRequest);
    }

    @PostMapping("testCmd")
    public void testCmd(){
        SshUtils.testCmd();
    }

    @PostMapping("testLimit")
    public String testLimit(String msg){
        System.out.println("msg:"+msg);
        return "success";
    }

    @RequestMapping("/a")
    public Map<Object,Object> a(HttpServletResponse resp){
        resp.setHeader("Cache-Control","public,max_age=30");
        System.out.println("--进入方法--");
        Map info=MapUtil.of("address","地址1");
        return MapUtil.builder()
                .put("name","test1")
                .put("info",info)
                .build();
    }

    @RequestMapping("/b")
    public Map<Object,Object> b(HttpServletRequest req){
        Enumeration headers=req.getHeaderNames();
        while(headers.hasMoreElements()){
            String headerName=headers.nextElement().toString();
            String headerValue=req.getHeader(headerName);
            System.out.printf("%s:%s%n", headerName,headerValue);
        }
        Map info=MapUtil.of("email","qq.com");
        return MapUtil.builder()
                .put("age",22)
                .put("name","test2")
                .put("info",info)
                .build();
    }

    @PostMapping("/step1")
    public Map<Object,Object> step1(){
        return MapUtil.builder()
                .put("age",22)
                .put("name","yuki")
                .put("userId",1)
                .build();
    }

    @PostMapping("/step2/{userId}")
    public Map<Object,Object> step2(@PathVariable String userId){
        System.out.println("userId:"+userId);
        return MapUtil.builder()
                .put("address","南京")
                .put("email","yuki@qq.com")
                .put("userId",1)
                .build();
    }
}
