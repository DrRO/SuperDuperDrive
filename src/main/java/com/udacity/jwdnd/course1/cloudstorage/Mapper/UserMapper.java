package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Models.UserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // Query for user id
    @Select("SELECT * FROM USERS WHERE userid = #{userId}")
    UserModel getUserId(Integer userId);

    // Query for get users list
    @Select("SELECT * FROM USERS")
    List<UserModel> getAll();

   //Query  for getting username
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    UserModel getUser(String username);

  //Insert new User
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(UserModel user);
}
