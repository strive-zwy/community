package com.zwy.controller;

import com.zwy.cache.HotTagCache;
import com.zwy.config.IpUtil;
import com.zwy.dto.PageDTO;
import com.zwy.dto.QuestionDTO;
import com.zwy.model.Question;
import com.zwy.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page" , defaultValue = "1") Integer page,
                        @RequestParam(name = "size" , defaultValue = "5") Integer size,
                        @RequestParam(name = "search" , required = false) String search,
                        @RequestParam(name = "tag" , required = false) String tag) {
        PageDTO<QuestionDTO> questionPage = questionService.list(search,tag,page,size,0L);
        List<Question> hotList = questionService.findHotList();
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("questionPage",questionPage);
        model.addAttribute("hotList",hotList);
        model.addAttribute("tags",tags);
        if (StringUtils.isNotBlank(search)){
            model.addAttribute("searchkw",search);
        }
        if (StringUtils.isNotBlank(tag)){
            model.addAttribute("tagkw",tag);
        }
        return "index";
    }


}
