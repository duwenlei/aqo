package com.grape.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duwenlei
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("test1")
    public String test1() {
        return "6666";
    }
}
