package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.Models.UserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    // Query for Note id
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    NoteModel getNoteId(Integer noteId);

    // Query for existing notes List
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<NoteModel> getExistingNotes(Integer userId);

    // Query for Note Titles List
    @Select("SELECT * FROM NOTES WHERE noteTitle = #{noteTitle}")
    List<NoteModel> getExistingTitles(String noteTitle);

    // Insert new Note
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " + "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(NoteModel note);


    // update  Note
    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteId}")
    void updateNote(Integer noteId, String title, String description);

    // Delete Note
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteNote(Integer noteId);





}
