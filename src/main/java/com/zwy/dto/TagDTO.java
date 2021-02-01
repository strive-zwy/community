package com.zwy.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/1/31 17:00
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class TagDTO {

    private String idName;
    private String categoryName;
    private List<String> tags;

}
