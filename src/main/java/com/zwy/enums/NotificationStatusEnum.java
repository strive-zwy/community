package com.zwy.enums;

/**
 * @Author ：zwy
 * @Date ：2021/2/1 13:36
 * @Version ：1.0
 * @Description ：TODO
 **/

public enum  NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
