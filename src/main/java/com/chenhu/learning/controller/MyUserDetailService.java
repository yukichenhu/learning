package com.chenhu.learning.controller;

import com.chenhu.learning.entity.Role;
import com.chenhu.learning.entity.User;
import com.chenhu.learning.repository.RoleRepository;
import com.chenhu.learning.repository.UserRepository;
import com.chenhu.learning.vo.UserDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈虎
 * @date 2022-06-24 9:31
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUserByName(username);
        if (ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户信息不存在");
        }
        List<Role> roles=roleRepository.findRoles(user.getId());
        UserDetail userDetail=new UserDetail();
        BeanUtils.copyProperties(user,userDetail);
        userDetail.setRoles(roles);
        return userDetail;
    }
}
