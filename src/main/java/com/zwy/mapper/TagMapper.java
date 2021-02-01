package com.zwy.mapper;

import com.zwy.model.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface TagMapper {

    @Select("select * from tag")
    List<Tag> getTagList();

    @Insert("INSERT INTO tag (id,  tagName,  gmt_create, gmt_modified" +
            "  values  ( #{id}, #{tagName}, #{gmtCreate}, #{gmtModified}, )")
    void insert (Tag tag);

}
