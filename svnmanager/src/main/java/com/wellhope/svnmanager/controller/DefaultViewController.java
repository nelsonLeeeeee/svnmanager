package com.wellhope.svnmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author nelsonLee
 * @date 2018/11/1 10:05
 */
@Controller
@RequestMapping("/")
public class DefaultViewController {

    @RequestMapping("/")
    public String welcome() {
        return "redirect:/login/api/toLoginPage";
    }
}
