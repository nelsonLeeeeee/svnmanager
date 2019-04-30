package com.wellhope.svnmanager.aspect;

import com.wellhope.svnmanager.exception.AuthorizeException;
import com.wellhope.svnmanager.service.LoginService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * @author nelsonLee
 * @date 2018/11/1 11:01
 */
@Aspect
@Component
public class CheckLoginAspect {

    @Autowired
    private LoginService loginService;

    @Pointcut("execution(public * com.wellhope.svnmanager.controller.*.*(..)) " +
            "&& !execution(public * com.wellhope.svnmanager.controller.LoginController.*(..))")
    public void check(){
    }


    @Before("check()")
    public void doBefore() throws AuthorizeException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equalsIgnoreCase("token")){
                    String code = new String(Base64.getDecoder().decode(cookie.getValue()));
                    String userName = code.substring(0,code.indexOf("SVN"));
                    String password = code.substring(code.indexOf("SVN")+3);
                    boolean result = this.loginService.nameLogin(userName,password);
                    if(result){
                        return;
                    }else{
                        throw new AuthorizeException();
                    }
                }
            }
        }
        throw new AuthorizeException();
    }

    @AfterReturning(pointcut = "check()")
    public void doAfterReturning() {
    }


}
