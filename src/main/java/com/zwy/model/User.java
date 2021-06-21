package com.zwy.model;

import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/20 13:44
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class User {

    public static final int LOGIN_TYPE_USER = 0;
    public static final int LOGIN_TYPE_GITHUB = 1;

    private Long id;
    private String name;
    private String bio;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private int loginType;
    private String password;
}
