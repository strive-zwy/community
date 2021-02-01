package com.zwy.model;

import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/1/31 15:42
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class Tag {

    private Integer id;
    private String tagName;
    private Long gmtCreate;
    private Long gmtModified;

}
