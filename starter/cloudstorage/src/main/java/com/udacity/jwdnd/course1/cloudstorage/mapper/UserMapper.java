package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(User user);

    @Update("UPDATE USERS SET username=#{username}, password=#{password}, salt=#{salt}, firstName=#{firstName}, lastName=#{lastName} WHERE userid=#{userid}")
    void update(User user);

    @Delete("DELETE FROM USERS WHERE username=#{username}")
    void delete(String username);
}
