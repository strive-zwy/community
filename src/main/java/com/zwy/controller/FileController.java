package com.zwy.controller;

import com.zwy.config.OSSClientUtil;
import com.zwy.dto.FileDTO;
import com.zwy.exception.CustomizeErrorCode;
import com.zwy.exception.CustomizeException;
import com.zwy.model.User;
import com.zwy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author ：zwy
 * @Date ：2021/2/1 20:09
 * @Version ：1.0
 * @Description ：TODO
 **/
@Controller
public class FileController {

    @Autowired
    private OSSClientUtil ossClientUtil;
    @Autowired
    private UserService userService;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        if (file == null) throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        String fileUrl = ossClientUtil.uploadImg2Oss(file);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(ossClientUtil.getImgUrl(fileUrl));
        return fileDTO;
    }

    @PostMapping("/file/imgUpload")
    public String imgUpload(HttpServletRequest request,
                            HttpServletResponse response, Long id, Model model){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("image");
        if (file == null) throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        String fileUrl = ossClientUtil.uploadImg2Oss(file);
        User user = userService.findById(id);
        user.setAvatarUrl(ossClientUtil.getImgUrl(fileUrl));
        user.setGmtModified(System.currentTimeMillis());
        userService.updateAvatarUrl(user);
        return "redirect:/self";
    }

}
