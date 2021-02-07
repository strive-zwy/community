package com.zwy.service;

import com.zwy.dto.PageDTO;
import com.zwy.dto.QuestionDTO;
import com.zwy.dto.SearchDTO;
import com.zwy.exception.CustomizeErrorCode;
import com.zwy.exception.CustomizeException;
import com.zwy.mapper.QuestionMapper;
import com.zwy.mapper.UserMapper;
import com.zwy.model.Question;
import com.zwy.model.User;
import org.apache.commons.lang3.StringUtils;
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


    public PageDTO<QuestionDTO> list(String search,String tag, Integer page, Integer size , Long userId) {
        Integer offset = size * (page - 1);
        List<Question> list;
        Integer totleCount;
        SearchDTO searchDTO = new SearchDTO();
        if (userId == 0){
            if (StringUtils.isNotBlank(search)){
                searchDTO.setOffset(offset);
                searchDTO.setSearch(search);
                searchDTO.setSize(size);
                if (StringUtils.isNotBlank(tag)){
                    searchDTO.setTag(tag);
                    totleCount = questionMapper.searchTagCount(search);
                    list = questionMapper.searchTagList(searchDTO);
                }else{
                    totleCount = questionMapper.searchCount(search);
                    list = questionMapper.searchList(searchDTO);
                }
            }else{
                if (StringUtils.isNotBlank(tag)){
                    totleCount = questionMapper.tagCount(tag);
                    list = questionMapper.tagList(tag,offset,size);
                }else{
                    list = questionMapper.list(offset,size);
                    totleCount = questionMapper.count();
                }
            }
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

    public QuestionDTO findById(Long id) {
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

    public Question findQueById(Long id) {
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

    public void addViewCount(Long id) {
        questionMapper.addViewCount(id);
    }

    public void addCommentCount(Long id) {
        questionMapper.addCommentCount(id);
    }

    public List<QuestionDTO> findLikeList(Long id,String tag) {
        String tags = tag.replace("、","|");
        List<Question> list = questionMapper.findLikeList(id,tags);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question q : list ) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findById(q.getCreator());
            BeanUtils.copyProperties(q,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    public List<Question> findHotList() {
        return questionMapper.findHotList();
    }
}
