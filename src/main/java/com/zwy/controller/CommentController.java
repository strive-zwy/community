package com.zwy.controller;

import com.zwy.dto.CommentCreateDTO;
import com.zwy.dto.LikeDTO;
import com.zwy.dto.ResultDTO;
import com.zwy.exception.CustomizeErrorCode;
import com.zwy.mapper.NotificationMapper;
import com.zwy.model.User;
import com.zwy.service.CommentService;
import com.zwy.service.LikeLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ：zwy
 * @Date ：2021/1/27 19:17
 * @Version ：1.0
 * @Description ：评论
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeLogService likeLogService;

    @RequestMapping(value = "/comment" ,method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        commentService.insert(commentCreateDTO,user.getId());
        Map<Object,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("message","成功");
        return map;
    }

    @RequestMapping(value = "/like" ,method = RequestMethod.POST)
    @ResponseBody
    public Object like(@RequestBody LikeDTO likeDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        likeDTO.setLiker(user.getId());
        likeDTO.setStatus(1);
        int i = likeLogService.like(likeDTO);
        Map<Object,Object> map = new HashMap<>();
        if (i == 0) {
            map.put("code",2001);
            map.put("message","您已取消点赞！");
        }else {
            map.put("code",2002);
            map.put("message","点赞成功！");
        }
        return map;
    }
}
