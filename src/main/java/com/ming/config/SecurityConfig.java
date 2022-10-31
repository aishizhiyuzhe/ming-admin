package com.ming.config;

import com.ming.mapper.UserInfoMapper;
import com.ming.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;
    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置了这个就会将没有授权的页面直接跳转过来
        http.formLogin()
//                .loginPage("/index") // 配置哪个 url 为登录页面
                .loginProcessingUrl("/login") // 设置哪个是登录的 url。
                .successForwardUrl("/index") // 登录成功之后跳转到哪个 url
                .failureForwardUrl("/fail");// 登录失败之后跳转到哪个 url

        http.authorizeRequests().antMatchers("/admin/login")
                .permitAll();
//                // todo 肯定是可以通过读取数据库中的数据来控制权限
//                //有role可以访问
////                .antMatchers("/role1").hasAuthority("role")
//                //这个是配置多个
////                .antMatchers("/role2").hasAnyAuthority("role","role1")
//                //使用这个需要在权限配置上加上ROLE_
//                //hasRole和hasAuthority更多的是概念上去区别,role更多是的是为角色,而Authority为权限
//                .antMatchers("/role1").hasRole("管理员")
//                .antMatchers("/role2").hasAnyAuthority("menu:system","menu:user")
//                .anyRequest() // 其他请求
//                .authenticated();//需要认证

        http.exceptionHandling().accessDeniedPage("/unauth");

        // 开启记住我功能
        http.rememberMe()
                .tokenValiditySeconds(10)
                .tokenRepository(tokenRepository)
                .userDetailsService(loginService);

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
//
//        // 关闭 csrf
//        http.csrf().disable();

    }
}
