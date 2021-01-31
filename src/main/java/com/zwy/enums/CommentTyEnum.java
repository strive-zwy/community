package com.zwy.enums;

/**
 * @Author ：zwy
 * @Date ：2021/1/28 16:51
 * @Version ：1.0
 * @Description ：TODO
 **/

public enum  CommentTyEnum {

    QUESTION(1),
    COMMENT(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTyEnum(Integer type){
        this.type = type;
    }

    public static boolean isExist(Integer type){
        for (CommentTyEnum c : CommentTyEnum.values()){
            if (c.getType().equals(type)) return true;
        }
        return false;
    }
}
