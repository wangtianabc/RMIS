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
        ErrorInfo<Object> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData(e.getStackTrace());
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}
