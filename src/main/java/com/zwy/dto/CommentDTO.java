package com.zwy.dto;

import com.zwy.model.User;
import lombok.Data;

import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/1/29 19:55
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class CommentDTO {

    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private List<CommentDTO> commList;
    private User user;

}
