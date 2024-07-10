package com.hyj.cloud.handler;

import com.hyj.cloud.common.api.ErrorResult;
import com.hyj.cloud.common.api.R;
import com.hyj.cloud.common.api.ResultCode;
import com.hyj.cloud.pojo.BizException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException e) {
        logger.error("MultipartException: ", e);
        return "File upload error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder(bindingResult.getFieldErrors().size() * 16);
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            if (i > 0) {
                errorMessage.append(",");
            }
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            errorMessage.append(fieldError.getDefaultMessage());
        }
        R msg = R.fail(ResultCode.PARAM_IS_INVALID.code(),errorMessage.toString());
        logger.info("paramValidateError:{}", msg);
        return msg;
    }
    @ExceptionHandler(BizException.class)
    public ErrorResult bizExceptionHandler(BizException e, HttpServletRequest request) {
        log.error("发生业务异常！原因是: {}", e.getMessage());
        return ErrorResult.fail(e.getCode(), e.getMessage());
    }

}
