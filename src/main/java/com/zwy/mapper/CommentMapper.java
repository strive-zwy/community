package com.zwy.mapper;

import com.zwy.dto.CommentCreateDTO;
import com.zwy.model.Comment;
import com.zwy.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommentMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Jan 27 19:33:16 CST 2021
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Jan 27 19:33:16 CST 2021
     */
    int insert(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Jan 27 19:33:16 CST 2021
     */
    Comment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Jan 27 19:33:16 CST 2021
     */
    List<Comment> selectAll();

    // 通过问题 id 获取评论
    List<Comment> selectByQuestionId(CommentCreateDTO createDTO);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Jan 27 19:33:16 CST 2021
     */
    int updateByPrimaryKey(Comment record);
}
