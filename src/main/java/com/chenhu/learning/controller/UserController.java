package com.chenhu.learning.controller;

import com.chenhu.learning.bo.UserBO;
import com.chenhu.learning.config.MyPageRequest;
import com.chenhu.learning.entity.Post;
import com.chenhu.learning.entity.User;
import com.chenhu.learning.query.QueryWrapper;
import com.chenhu.learning.repository.PostRepository;
import com.chenhu.learning.repository.UserRepository;
import com.chenhu.learning.utils.PropertyUtils;
import com.chenhu.learning.utils.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 陈虎
 */
@RestController
@RequestMapping("test")
public class UserController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private QueryUtils queryUtils;

    @Resource
    private PostRepository postRepository;

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
}
