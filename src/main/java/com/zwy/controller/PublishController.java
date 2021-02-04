package com.zwy.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.zwy.cache.TagCache;
import com.zwy.config.OSSClientUtil;
import com.zwy.dto.FileDTO;
import com.zwy.model.Question;
import com.zwy.model.Tag;
import com.zwy.model.User;
import com.zwy.service.QuestionService;
import com.zwy.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author ：zwy
 * @Date ：2021/1/22 19:08
 * @Version ：1.0
 * @Description ：发布
 **/
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private TagService tagService;
    @Autowired
    private OSSClientUtil ossClientUtil;

    @GetMapping("/publish/{id}")
    private String publish(@PathVariable(value = "id") Long id,
                           Model model) {
        if (id != null && id != 0) {
            Question q = questionService.findQueById(id);
            model.addAttribute("title", q.getTitle());
            model.addAttribute("description", q.getDescription());
            model.addAttribute("tag", q.getTag());
            model.addAttribute("id", id);
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
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
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
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (id != null && id != 0) {
            Question q = questionService.findQueById(id);
            q.setDescription(description);
            q.setTitle(title);
            q.setTag(tag);
            q.setGmtModified(System.currentTimeMillis());
            questionService.update(q);
            return "redirect:/question/" + id + "/0";
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


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request)
            throws IllegalStateException, IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        Map<String, Object> result = new HashMap<String, Object>();

        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile f = multiRequest.getFile(iter.next());
                if (f != null) {
                    //将图片上传到阿里云OSS对象云存储，而后根据文件名生成图片链接
                    String name = ossClientUtil.uploadImg2Oss(f);
                    String imgUrl = ossClientUtil.getImgUrl(name);
                    //本文使用的是editor.Md开源的markdown编辑器，根据其文档说明 图片上传成功返回json数据给editor.Md
                    result.put("success", 1);
                    result.put("message", "上传成功");
                    result.put("url", imgUrl);
                }
            }
        } else {
            //图片上传失败返回json数据给editor.Md
            result.put("success", 0);
            result.put("message", "上传失败");
        }
        System.out.println(result.toString());
        return result;
    }


}
