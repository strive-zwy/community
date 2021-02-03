package com.zwy.controller;

import com.zwy.dto.NotificationDTO;
import com.zwy.dto.PageDTO;
import com.zwy.dto.QuestionDTO;
import com.zwy.model.User;
import com.zwy.service.NotificationService;
import com.zwy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ：zwy
 * @Date ：2021/1/25 15:53
 * @Version ：1.0
 * @Description ：TODO
 **/
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    private String profile(@PathVariable(name = "action") String action,
                           @RequestParam(name = "page" , defaultValue = "1") Integer page,
                           @RequestParam(name = "size" , defaultValue = "5") Integer size,
                           HttpServletRequest request,
                           Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if ("question".equals(action)){
            model.addAttribute("section" , "question");
            model.addAttribute("sectionName" , "我的提问");
            PageDTO<QuestionDTO> questionPage = questionService.list("",page,size,user.getId());
            model.addAttribute("questionPage",questionPage);
        }
        if ("replies".equals(action)){
            model.addAttribute("section" , "replies");
            model.addAttribute("sectionName" , "最新回复");
            PageDTO<NotificationDTO> notificationPage = notificationService.list(page,size,user.getId());
            model.addAttribute("notificationPage",notificationPage);
        }
        return "/profile";
    }

}
