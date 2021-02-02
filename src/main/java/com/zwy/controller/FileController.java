package com.zwy.controller;

import com.zwy.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ：zwy
 * @Date ：2021/2/1 20:09
 * @Version ：1.0
 * @Description ：TODO
 **/
@Controller
public class FileController {

    @RequestMapping("/file/upload")
    public FileDTO upload(){
        return new FileDTO();
    }

}
