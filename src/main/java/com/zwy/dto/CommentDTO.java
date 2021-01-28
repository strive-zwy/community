package com.zwy.dto;

import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/27 19:34
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class CommentDTO {

    private Long parentId;
    private String content;
    private Integer type;

}
