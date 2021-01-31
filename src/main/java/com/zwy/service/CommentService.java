package com.zwy.service;

import com.zwy.dto.CommentCreateDTO;
import com.zwy.dto.CommentDTO;
import com.zwy.enums.CommentTyEnum;
import com.zwy.exception.CustomizeErrorCode;
import com.zwy.exception.CustomizeException;
import com.zwy.mapper.CommentMapper;
import com.zwy.model.Comment;
import com.zwy.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @Transactional
    public void insert(CommentCreateDTO commentCreateDTO, Long id) {
        if (commentCreateDTO.getParentId() == null || commentCreateDTO.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (commentCreateDTO.getType() == null || !CommentTyEnum.isExist(commentCreateDTO.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(id);
        comment.setLikeCount(0L);
        if (commentCreateDTO.getType().equals(CommentTyEnum.COMMENT.getType())){
            // 回复评论
            Comment c = commentMapper.selectByPrimaryKey(commentCreateDTO.getParentId());
            if (c == null)  throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            commentMapper.insert(comment);
        }else {
            // 回复问题
            Question question = questionService.findQueById(commentCreateDTO.getParentId());
            if (question == null) throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            commentMapper.insert(comment);
            questionService.addCommentCount(question.getId());
        }
    }

    public List<CommentDTO> getCommList(Long qId) {
        CommentCreateDTO createDTO = new CommentCreateDTO();
        createDTO.setParentId(qId);
        createDTO.setType(1);
        List<Comment> list = commentMapper.selectByQuestionId(createDTO);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment c : list){
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(c,commentDTO);
            commentDTO.setUser(userService.findById(c.getCommentator()));
            commentDTOS.add(commentDTO);
            createDTO.setParentId(c.getId());
            createDTO.setType(2);
            List<Comment> comments = commentMapper.selectByQuestionId(createDTO);
            List<CommentDTO> commentDTOList = new ArrayList<>();
            for (Comment cc : comments){
                CommentDTO ccc = new CommentDTO();
                BeanUtils.copyProperties(cc,ccc);
                ccc.setUser(userService.findById(c.getCommentator()));
                commentDTOList.add(ccc);
            }
            commentDTO.setCommList(commentDTOList);
        }
        return commentDTOS;
    }
}
