package com.zwy.exception;

/**
 * @Author ：zwy
 * @Date ：2021/1/26 21:05
 * @Version ：1.0
 * @Description ：TODO
 **/

public class CustomizeException extends RuntimeException {

    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode) {
        this.message = iCustomizeErrorCode.getMessage();
        this.code = iCustomizeErrorCode.getCode();
    }

    /*public CustomizeException(Integer code,String message) {
        this.code = code;
        this.message = message;
    }*/

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
