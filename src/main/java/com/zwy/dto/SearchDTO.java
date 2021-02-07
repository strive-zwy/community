package com.zwy.dto;

import lombok.Data;

/**
 * @Author ：zwy
 * @Date ：2021/2/2 21:17
 * @Version ：1.0
 * @Description ：TODO
 **/
@Data
public class SearchDTO {

    private String search;
    private String tag;
    private Integer offset;
    private Integer size;
}
