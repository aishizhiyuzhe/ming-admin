package com.ming.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.entity.Menu;
import com.ming.entity.Role;
import com.ming.entity.Users;
import com.ming.mapper.UserInfoMapper;
import com.ming.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> qw=new QueryWrapper<>();
        qw.eq("username",username);
        Users user = userMapper.selectOne(qw);

        if (null== user){
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<Role> roles=userInfoMapper.selectRoleByUserId(Long.valueOf(user.getId()));
        List<Menu> menus=userInfoMapper.selectMenuByUserId(Long.valueOf(user.getId()));
        List<GrantedAuthority> auths =new ArrayList<>();
        for (Role role:roles){
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority("ROLE_"+role.getName());
            auths.add(simpleGrantedAuthority);
        }

        for (Menu menu:menus){
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(menu.getPermission());
            auths.add(simpleGrantedAuthority);
        }

        UserDetails userDetails=new User(username,user.getPassword(), auths);
        return userDetails;
    }
}
