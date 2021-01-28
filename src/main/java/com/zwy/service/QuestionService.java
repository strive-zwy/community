package com.zwy.service;

import com.zwy.dto.PageDTO;
import com.zwy.dto.QuestionDTO;
import com.zwy.exception.CustomizeErrorCode;
import com.zwy.exception.CustomizeException;
import com.zwy.mapper.QuestionMapper;
import com.zwy.mapper.UserMapper;
import com.zwy.model.Question;
import com.zwy.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/1/24 19:34
 * @Version ：1.0
 * @Description ：TODO
 **/
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;


    public PageDTO<QuestionDTO> list(Integer page, Integer size , Integer userId) {
        Integer offset = size * (page - 1);
        List<Question> list;
        Integer totleCount;
        if (userId == 0){
            list = questionMapper.list(offset,size);
            totleCount = questionMapper.count();
        }else{
            list = questionMapper.listByCreator(userId,offset,size);
            totleCount = questionMapper.countByCreator(userId);
        }
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO<QuestionDTO> questionDTOPageDTO = new PageDTO<>();
        for (Question q : list ) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findById(q.getCreator());
            BeanUtils.copyProperties(q,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        questionDTOPageDTO.setList(questionDTOList);
        questionDTOPageDTO.setPagination(totleCount,page,size);
        return questionDTOPageDTO;
    }

    public QuestionDTO findById(Integer id) {
        Question question = questionMapper.findById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO dto = new QuestionDTO();
        User u = userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question,dto);
        dto.setUser(u);
        return dto;
    }

    public Question findQueById(Integer id) {
        Question question = questionMapper.findById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        return question;
    }

    public void create(Question question){
        int a = questionMapper.create(question);
        if (a != 1){
            throw new CustomizeException(CustomizeErrorCode.CREATE_OR_UPDATE_FAIOL);
        }
    }

    public void update(Question question){
        int a = questionMapper.update(question);
        if (a != 1){
            throw new CustomizeException(CustomizeErrorCode.CREATE_OR_UPDATE_FAIOL);
        }
    }

    public void addViewCount(Integer id) {
        questionMapper.addViewCount(id);
    }
}
