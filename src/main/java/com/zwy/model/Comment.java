package com.zwy.model;

import lombok.Data;

@Data
public class Comment {

    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;

}
