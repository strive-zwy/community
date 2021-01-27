package com.zwy.dto;

import com.zwy.model.User;
import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/24 19:35
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class QuestionDTO {

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
    private User user;
}
