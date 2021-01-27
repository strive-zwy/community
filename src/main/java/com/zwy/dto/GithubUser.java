package com.zwy.dto;

import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/20 10:36
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class GithubUser {

    private String name;
    private long id;
    private String bio;
    private String avatar_url;

}
