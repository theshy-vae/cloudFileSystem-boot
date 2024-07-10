package com.hyj.cloud.pojo;

import com.hyj.cloud.common.api.ResultCode;
import lombok.Data;

@Data
public class AuthException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public AuthException() {
        super();
    }

    public AuthException(ResultCode resultCode) {
        super(resultCode.message());
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public AuthException(ResultCode resultCode, Throwable cause) {
        super(resultCode.message(), cause);
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public AuthException(String message) {
        super(message);
        this.code = 401;
        this.message = message;
    }

    public AuthException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public AuthException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    
}
