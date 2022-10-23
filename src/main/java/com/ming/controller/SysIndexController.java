package com.ming.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SysIndexController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
