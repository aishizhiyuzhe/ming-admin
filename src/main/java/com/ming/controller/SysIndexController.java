package com.ming.controller;

import com.ming.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SysIndexController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "index";
    }


    @RequestMapping("/fail")
    @ResponseBody
    public String fail(){
        return "fail";
    }

    @RequestMapping("/role1")
    @ResponseBody
    @Secured({"ROLE_管理员","ROLE_普通用户"})
    public String role1(){
        return "role1";
    }

    @RequestMapping("/role2")
    @ResponseBody
    @Secured("ROLE_管理员")
    public String role2(){
        return "role2";
    }

    @RequestMapping("/unauth")
    @ResponseBody
    public String unauth(){
        return "没有权限访问";
    }

    @RequestMapping("/preAuthorizeTest")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('menu:system')")
    public String preAuthorizeTest(){
        return "preAuthorizeTest";
    }

    @RequestMapping("/postAuthorizeTest")
    @ResponseBody
    @PostAuthorize("hasAnyAuthority('menu:system')")
    public String postAuthorizeTest(){
        System.out.print("验证前打印");
        return "post";
    }

    //PostFilter测试
    @RequestMapping("/getAll")
    @ResponseBody
    @PostAuthorize("hasAnyAuthority('menu:system')")
    @PostFilter("filterObject.username == 'admin1'")
    public List postFilter(){
        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(1,"admin1","6666"));
        list.add(new Users(2,"admin2","888"));
        return list;
    }

    @RequestMapping("/getTestPreFilter")
    @PreAuthorize("hasRole('ROLE_管理员')")
    @PreFilter(value = "filterObject.id%2==0")
    @ResponseBody
    public List<Users> getTestPreFilter(@RequestBody List<Users>
                                                   list){
        list.forEach(t-> {
            System.out.println(t.getId()+"\t"+t.getUsername());
        });
        return list;
    }
}
