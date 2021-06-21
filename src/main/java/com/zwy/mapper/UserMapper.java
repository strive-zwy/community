package com.zwy.mapper;

import com.zwy.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author ：zwy
 * @Date ：2021/1/20 13:42
 * @Version ：1.0
 * @Description ：TODO
 **/
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,bio,account_id,token,gmt_create,gmt_modified,avatar_url,loginType,password) values" +
            " (#{name},#{bio},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{loginType},#{password})")
    void insert(User user);

    @Update("update user set token = #{token},gmt_modified = #{gmtModified} where  id = #{id}")
    void update(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{acountId}")
    User findByAccount(@Param("acountId") String acountId);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from user where name = #{name}")
    User getUserByUsername(String name);
}
