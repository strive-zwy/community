package com.zwy.dto;

/**
 * @Author ：zwy
 * @Date ：2021/1/20 10:36
 * @Version ：1.0
 * @Description ：TODO
 **/

public class GithubUser {

    private String name;
    private long id;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
