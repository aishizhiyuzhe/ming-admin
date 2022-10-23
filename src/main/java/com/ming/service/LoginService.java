package com.ming.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        String pwd="$2a$10$JW/hOXH2UWuM1f/dlIOXVOqfmgJqXsvCjRvqxga1VErsF4EUKRDGi";
        UserDetails userDetails=new User(username,pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return userDetails;
    }
}
