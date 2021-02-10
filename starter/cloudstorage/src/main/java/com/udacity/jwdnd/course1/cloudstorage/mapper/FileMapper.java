package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{filename} and userid = #{userid}")
    File getFile(String filename, Integer userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFilesList(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(File file);

    @Update("UPDATE FILES SET filename=#{filename}, contenttype=#{contenttype}, filesize=#{filesize}, userid=#{userid}, filedata=#{filedata} WHERE fileid=#{fileid}")
    void update(File file);

    @Delete("DELETE FROM FILES WHERE filename=#{filename} and userid = #{userid}")
    void delete(String filename, Integer userid);
}
