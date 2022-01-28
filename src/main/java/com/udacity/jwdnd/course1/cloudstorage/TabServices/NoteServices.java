package com.udacity.jwdnd.course1.cloudstorage.TabServices;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Models.NoteModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServices {

    private  NoteMapper noteMapper;
    private  UserMapper userMapper;


    public NoteServices(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public void createNote(String title, String description, String userName) {
        noteMapper.insertNote(new NoteModel(0, title, description, userMapper.getUser(userName).getUserId()));
    }

    //Get Note info from NoteMapper
    public List<NoteModel> getNoteListings(Integer userId) {

        return noteMapper.getExistingNotes(userId);
    }

    //Get Note List according to Titles  from NoteMapper
    public List<NoteModel> getExistingTitles(String noteTitle) {

        return noteMapper.getExistingTitles(noteTitle);
    }



    public NoteModel getNote(Integer noteId) {
        return noteMapper.getNoteId(noteId);
    }

    // Update Note
    public void updateNote(Integer noteId, String title, String description) {
        noteMapper.updateNote(noteId, title, description);
    }

    //Delete note
    public void deleteNote(Integer noteId) {

        noteMapper.deleteNote(noteId);
    }


}
