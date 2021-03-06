package com.liyk.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiYongkui
 * @title: HelloController
 * @description: TODO
 * @date 2021-05-29 02912:25
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/employee/base/hello")
    public String hello2(){
        return "/employee/base/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello3(){
        return "/employee/advanced/hello";
    }
}
