package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;


@Slf4j
@ControllerAdvice
@ResponseBody //将返回值转换成JSON对象返回给前端
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        if(ex.getMessage().contains("Duplicate entry")){
            String[] split=ex.getMessage().split(" ");
            String msg= split[2]+"已存在";
            return  R.error(msg);
        }
        return  R.error("unknow error");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHander(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }




}
