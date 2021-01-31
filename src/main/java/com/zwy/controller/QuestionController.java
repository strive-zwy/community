package com.zwy.controller;

import com.zwy.dto.CommentDTO;
import com.zwy.dto.QuestionDTO;
import com.zwy.service.CommentService;
import com.zwy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/1/26 17:01
 * @Version ：1.0
 * @Description ：TODO
 **/
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model){
        QuestionDTO dto = questionService.findById(id);
        questionService.addViewCount(id);
        dto.setViewCount(dto.getViewCount() + 1);
        List<CommentDTO> commList = commentService.getCommList(id);
        List<QuestionDTO> likeQlist = questionService.findLikeList(dto.getId(),dto.getTag());
        model.addAttribute("question",dto);
        model.addAttribute("commList",commList);
        model.addAttribute("likeQlist",likeQlist);
//        model.addAttribute("inddd",5);
        return "question";
    }
}
