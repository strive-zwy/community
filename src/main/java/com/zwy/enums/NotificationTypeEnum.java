package com.zwy.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    LIKE_QUESTION(3,"点赞了问题"),
    LIKE_COMMENT(4,"点赞了评论"),
    QUIT_LIKE_QUESTION(5,"取消点赞了问题"),
    QUIT_LIKE_COMMENT(6,"取消点赞了评论"),
    ;

    private int type;
    private String name;

    public static String nameOfType(Integer type) {
        for (NotificationTypeEnum n : NotificationTypeEnum.values()){
            if (n.getType() == type){
                return n.getName();
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
