package com.wellhope.svnmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author nelsonLee
 * @date 2018/11/1 11:45
 */
@ControllerAdvice
@Controller
public class AuthorizeException extends Throwable {

    @ExceptionHandler(value = AuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handlerAuthorizeException(){
        System.out.println("login");
        return "login";
    }
}
