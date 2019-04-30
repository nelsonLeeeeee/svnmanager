package com.wellhope.svnmanager.controller;

import com.wellhope.svnmanager.exception.AuthorizeException;
import com.wellhope.svnmanager.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;


/**
 * @author nelsonLee
 * @date 2018/10/23 15:54
 */
@Controller
@RequestMapping("login/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
        Cookie[] cookies = this.request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equalsIgnoreCase("token")){
                    String code = new String(Base64.getDecoder().decode(cookie.getValue()));
                    String userName = code.substring(0,code.indexOf("SVN"));
                    String password = code.substring(code.indexOf("SVN")+3);
                    boolean result = this.loginService.nameLogin(userName,password);
                    if(result){
                        return "redirect:/refer/api/findReferInfo?userName="+userName;
                    }else{
                        return "login.html";
                    }
                }
            }
        }

        return "login.html";
    }

    @RequestMapping("/toIndexPage")
    public String toIndexPage(){
        return "index.html";
    }



    /**
     * 登录
     * @param userName
     * @param password
     * @param response
     * @return
     */
    @RequestMapping("nameLogin")
    public String nameLogin(@RequestParam("userName") String userName,
                            @RequestParam("password") String password,
                            HttpServletResponse response){
        boolean result = this.loginService.nameLogin(userName,password);
        if(result){
            Cookie cookie = new Cookie("userName",userName);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 30);

            //使用账号+SVN+密码
            String code = userName + "SVN" + password;

            String tokenString = new String(Base64.getEncoder().encode(code.getBytes()));
            Cookie token = new Cookie("token",tokenString);
            token.setPath("/");
            token.setMaxAge(60 * 60 * 24 * 30);

            response.addCookie(cookie);
            response.addCookie(token);
            return "redirect:/refer/api/findReferInfo?userName="+userName;
        }
        return "redirect:/login/api/toLoginPage";
    }

    /**
     *注销
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("userName",null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        Cookie token = new Cookie("token",null);
        token.setPath("/");
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/login/api/toLoginPage";
    }

}
