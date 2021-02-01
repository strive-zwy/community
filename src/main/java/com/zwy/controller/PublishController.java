package com.zwy.controller;

import com.zwy.cache.TagCache;
import com.zwy.model.Question;
import com.zwy.model.Tag;
import com.zwy.model.User;
import com.zwy.service.QuestionService;
import com.zwy.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/1/22 19:08
 * @Version ：1.0
 * @Description ：TODO
 **/
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private TagService tagService;

    @GetMapping("/publish/{id}")
    private String publish(@PathVariable(value = "id") Long id,
                           Model model) {
        if (id != null && id != 0){
            Question q = questionService.findQueById(id);
            model.addAttribute("title",q.getTitle());
            model.addAttribute("description",q.getDescription());
            model.addAttribute("tag",q.getTag());
            model.addAttribute("id",id);
        }
        model.addAttribute("tagList", TagCache.get());
        return "/publish";
    }

    @PostMapping("/doPublish/{id}")
    private String doPublish(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request, Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tagList", TagCache.get());
        if (title == null || "".equals(title)) {
            model.addAttribute("error", "问题标题不能为空！");
            return "publish";
        }
        if (description == null || "".equals(description)) {
            model.addAttribute("error", "问题内容不能为空！");
            return "publish";
        }
        if (tag == null || "".equals(tag)) {
            model.addAttribute("error", "问题标签不能为空！");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (id != null && id != 0){
            Question q = questionService.findQueById(id);
            q.setDescription(description);
            q.setTitle(title);
            q.setTag(tag);
            q.setGmtModified(System.currentTimeMillis());
            questionService.update(q);
            return "redirect:/question/"+id;
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionService.create(question);
        return "redirect:/";
    }
}
