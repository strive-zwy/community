package com.zwy.exception;

/**
 * @Author ：zwy
 * @Date ：2021/1/26 21:26
 * @Version ：1.0
 * @Description ：TODO
 **/

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不见了"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYS_ERROR(2004,"服务器炸了"),
    TYPE_PARAM_NOT_FOUND(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"请填写回复内容！！！"),
    MESSAGE_NOT_FOUNT(2008,"消息不见了"),
    FILE_UPLOAD_FAIL(2009,"文件上传失败"),
    CREATE_OR_UPDATE_FAIOL(2010,"创建或更新失败了！")
    ;

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
