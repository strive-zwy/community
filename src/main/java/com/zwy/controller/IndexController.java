package com.zwy.controller;

import com.zwy.dto.PageDTO;
import com.zwy.dto.QuestionDTO;
import com.zwy.model.Question;
import com.zwy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zwy
 * @Date 2020/12/13 16:35
 * @Version 1.0
 * @Description 首页
 **/

@EnableAutoConfiguration
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page" , defaultValue = "1") Integer page,
                        @RequestParam(name = "size" , defaultValue = "5") Integer size) {
        PageDTO<QuestionDTO> questionPage = questionService.list(page,size,0L);
        List<Question> hotList = questionService.findHotList();
        model.addAttribute("questionPage",questionPage);
        model.addAttribute("hotList",hotList);
        return "index";
    }


}
