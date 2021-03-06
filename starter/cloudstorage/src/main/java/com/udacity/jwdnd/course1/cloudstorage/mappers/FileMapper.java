package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT filename FROM FILES WHERE userId=#{userId}")
    String[] getListByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    File getByName(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void add(File file);

    @Delete("DELETE FROM FILES WHERE fileName = #{fileName}")
    void delete(String fileName);
}
