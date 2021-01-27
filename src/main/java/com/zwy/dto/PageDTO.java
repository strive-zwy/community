package com.zwy.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/1/25 13:15
 * @Version ：1.0
 * @Description ：分页
 **/
@Data
public class PageDTO<T> {
    private List<T> list;

    private boolean showPrevious = true;
    private boolean showFirstPage = true;
    private boolean showNext = true;
    private boolean showEndPage = true;
    private Integer page;
    private Integer totlePage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totleCount, Integer page, Integer size) {
        this.page = page;
        this.totlePage = totleCount % size == 0 ? totleCount / size : totleCount / size + 1;
        for (int i = page-3; i < page+4; i++) {
            if (i > 0 && i <= totlePage) pages.add(i);
        }
        if (page == 1) showPrevious = false;
        if (page.equals(totlePage)) showNext = false;
        if (pages.contains(1)) showFirstPage = false;
        if (pages.contains(totlePage)) showEndPage = false;
    }
}
