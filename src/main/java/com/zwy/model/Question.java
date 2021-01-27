package com.zwy.model;

import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/22 20:15
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class Question {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
