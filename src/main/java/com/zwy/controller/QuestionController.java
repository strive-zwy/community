package com.zwy.controller;

import com.zwy.dto.CommentDTO;
import com.zwy.dto.QuestionDTO;
import com.zwy.exception.CustomizeErrorCode;
import com.zwy.exception.CustomizeException;
import com.zwy.model.Notification;
import com.zwy.service.CommentService;
import com.zwy.service.NotificationService;
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
 * @Description ：问题
 **/
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/question/{id}/{nId}")
    public String question(@PathVariable(name = "id") Long id,@PathVariable(name = "nId") Long nId ,Model model){
        QuestionDTO dto = questionService.findById(id);
        questionService.addViewCount(id);
        dto.setViewCount(dto.getViewCount() + 1);
        List<CommentDTO> commList = commentService.getCommList(id);
        List<QuestionDTO> likeQlist = questionService.findLikeList(dto.getId(),dto.getTag());
        model.addAttribute("question",dto);
        model.addAttribute("commList",commList);
        model.addAttribute("likeQlist",likeQlist);
        if (nId != 0){
            Notification nn = notificationService.findById(nId);
            if (nn == null) throw new CustomizeException(CustomizeErrorCode.MESSAGE_NOT_FOUNT);
            notificationService.read(nId);
            return "redirect:/question/"+id+"/0";
        }
            return "question";
    }
}
