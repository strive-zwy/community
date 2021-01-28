package com.zwy.service;

import com.zwy.dto.CommentDTO;
import com.zwy.mapper.CommentMapper;
import com.zwy.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：zwy
 * @Date ：2021/1/27 19:35
 * @Version ：1.0
 * @Description ：TODO
 **/
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;


    public void insert(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1);
        comment.setLikeCount(1L);
        comment.setId(2L);
        commentMapper.insert(comment);
    }
}
