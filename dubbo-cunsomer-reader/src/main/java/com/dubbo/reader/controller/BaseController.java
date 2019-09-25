package com.dubbo.reader.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * BaseController
 */
@ControllerAdvice
@RestController
public class BaseController {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass ());

    /**
     * 统一错误处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public String globalExceptionHandle(Exception e) {
        logger.error("发生异常:", e);
        return "错误信息："+e.getMessage ();
    }
}
