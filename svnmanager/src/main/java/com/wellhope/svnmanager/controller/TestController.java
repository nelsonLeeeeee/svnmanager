package com.wellhope.svnmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nelsonLee
 * @date 2018/10/23 15:47
 */

@RestController
@RequestMapping("test/api")
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello,Spring boot!";
    }

}
