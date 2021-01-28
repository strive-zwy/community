package com.zwy.controller;

import com.zwy.dto.QuestionDTO;
import com.zwy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model){
        QuestionDTO dto = questionService.findById(id);
        questionService.addViewCount(id);
        dto.setViewCount(dto.getViewCount() + 1);
        model.addAttribute("question",dto);
        return "question";
    }
}
