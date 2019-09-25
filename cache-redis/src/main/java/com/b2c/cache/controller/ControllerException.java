package com.b2c.cache.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;


@ControllerAdvice
@RestController
@RequestMapping( value = "/error")
public class ControllerException {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public String httpRequestMethodNotSupportedException(HttpServletResponse response) {
        response.setStatus(200);
        return "405 method 方法不支持";
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public String httpMediaTypeNotSupportedException(HttpServletResponse response) {
        response.setStatus(200);
        return "415 method 方法不支持";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundPage404(HttpServletResponse response) {
        response.setStatus(200);
        return "404 method 方法不支持";
    }

    @RequestMapping(value = "/401")
    public String forbidden401(HttpServletResponse response) {
        response.setStatus(200);
        System.out.println("401");
        return "401 method 方法不支持";
    }

    @RequestMapping(value = "/403")
    public String forbidden403(HttpServletResponse response) {
        response.setStatus(200);
        System.out.println("403");
        return "403 method 方法不支持";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defaultException(HttpServletResponse response) {
        response.setStatus(200);
        return "500 method 方法不支持";
    }
}
