package com.zwy.mapper;

import com.zwy.dto.SearchDTO;
import com.zwy.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (id,  title,  description,  gmt_create," +
            "  gmt_modified,  creator, tag )  values  ( #{id}, #{title}, #{description}, #{gmtCreate}, #{gmtModified}, " +
            "#{creator}, #{tag}  )")
    int create(Question question);

    @Select("select * from question order by  gmt_create desc limit #{offset},#{size} ")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where creator = #{userId}  order by  gmt_create desc  limit #{offset},#{size} ")
    List<Question> listByCreator(@Param(value = "userId") Long userId,
                                 @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByCreator(Long userId);

    @Select("select * from question where id = #{id} ")
    Question findById(Long id);

    @Update("update question set title = #{title},description = #{description},tag = #{tag}," +
            "gmt_modified = #{gmtModified} where  id = #{id}")
    int update(Question q);

    @Update("update question set like_count = #{likeCount} ," +
            "gmt_modified = #{gmtModified} where  id = #{id}")
    int updateLike(Question q);

    @Update("update question set view_Count = view_Count + 1 where  id = #{id}")
    void addViewCount(Long id);

    @Update("update question set comment_Count = comment_Count + 1 where  id = #{id}")
    void addCommentCount(Long id);

    @Select("select * from question WHERE tag REGEXP #{tags} AND id != #{id} order by  " +
            "comment_Count desc limit 8")
    List<Question> findLikeList(Long id,String tags);

    @Select("select * from question order by  comment_Count desc limit 8")
    List<Question> findHotList();

    @Select("select * from question WHERE title REGEXP #{search}  " +
            "  order by comment_Count desc limit #{offset},#{size}")
    List<Question> searchList(SearchDTO searchDTO);

    @Select("select count(1) from question WHERE title REGEXP #{search}  ")
    Integer searchCount(String search);

    @Select("select * from question WHERE title REGEXP #{search}  " +
            " OR tag REGEXP #{tag} order by comment_Count desc limit #{offset},#{size}")
    List<Question> searchTagList(SearchDTO searchDTO);

    @Select("select count(1) from question WHERE title REGEXP #{search}  " +
            " OR tag REGEXP #{tag}")
    Integer searchTagCount(String search);

    @Select("select count(1) from question WHERE tag REGEXP #{tag}")
    Integer tagCount(String tag);

    @Select("select * from question WHERE tag REGEXP #{tag} order by comment_Count desc limit #{offset},#{size}")
    List<Question> tagList(String tag, Integer offset, Integer size);
}
