package com.example.demo.mapper;

import com.example.demo.model.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
   @Insert("insert into gituser (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
   void insert(UserModel user);

   @Select("select * from gituser where token=#{token}")
   UserModel findByToken(@Param("token") String token);

    @Select("select * from gituser where id=#{id}")
    UserModel findbyid(@Param("id") Integer id);

    @Select("select * from gituser where account_id=#{id}")
    UserModel findByAccountId(Long id);

    @Update("update gituser set name=#{name} , token=#{token} ,avatar_url=#{avatarUrl},gmt_modified=#{gmtModified} where account_id=#{id}")
    void updateUser(UserModel userModel);

}
