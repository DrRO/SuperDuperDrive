package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.Models.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    FileModel getFileName(String fileName);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    String[] getFileList(Integer userId);


    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    List<FileModel> getExistingFiless(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " + "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(FileModel file);



    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    void deleteFile(String fileName);




}
