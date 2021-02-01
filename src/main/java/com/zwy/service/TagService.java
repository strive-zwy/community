package com.zwy.service;

import com.zwy.mapper.TagMapper;
import com.zwy.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ：zwy
 * @Date ：2021/1/31 15:53
 * @Version ：1.0
 * @Description ：TODO
 **/
@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public List<Tag> getTagList() {
        return tagMapper.getTagList();
    }
}
