package com.zwy.mapper;

import com.zwy.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (id,  title,  description,  gmt_create," +
            "  gmt_modified,  creator, tag )  values  ( #{id}, #{title}, #{description}, #{gmtCreate}, #{gmtModified}, " +
            "#{creator}, #{tag}  )")
    int create(Question question);

    @Select("select * from question limit #{offset},#{size} ")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where creator = #{userId} limit #{offset},#{size} ")
    List<Question> listByCreator(@Param(value = "userId") Integer userId,
                                 @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByCreator(Integer userId);

    @Select("select * from question where id = #{id} ")
    Question findById(Integer id);

    @Update("update question set title = #{title},description = #{description},tag = #{tag}," +
            "gmt_modified = #{gmtModified} where  id = #{id}")
    int update(Question q);
}
