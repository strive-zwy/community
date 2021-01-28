package com.zwy.controller;

import com.zwy.dto.CommentDTO;
import com.zwy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author ：zwy
 * @Date ：2021/1/27 19:17
 * @Version ：1.0
 * @Description ：TODO
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment" ,method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO){
        commentService.insert(commentDTO);
        System.out.println("插入评论");
        return null;
    }
}
