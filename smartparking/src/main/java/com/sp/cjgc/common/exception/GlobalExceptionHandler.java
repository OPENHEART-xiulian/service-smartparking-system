package com.sp.cjgc.common.exception;

import com.sp.cjgc.RespEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Zoey
 * @Date: 2024/1/4
 * @Time: 上午9:43
 * @Describe:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseObject bizExceptionHandler(HttpServletRequest req, BizException e) {
        e.printStackTrace();
        logger.error(("发生业务异常！原因是：{}"), e.getErrorMsg());
        return ResponseObject.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseObject bizExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        logger.error(("发生异常！原因是：{}"), e.getMessage());
        return ResponseObject.error(RespEnum.FAILURE.getCode(), e.getMessage());
    }
}
