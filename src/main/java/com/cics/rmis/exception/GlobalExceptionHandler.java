package com.cics.rmis.exception;

import com.cics.rmis.error.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<Object> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        return ErrorInfo.error("发生异常，请联系管理员。", req.getRequestURI());
    }
}
