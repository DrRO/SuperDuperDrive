package com.udacity.jwdnd.course1.cloudstorage.Contoller;

import com.udacity.jwdnd.course1.cloudstorage.Forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.Models.*;
import com.udacity.jwdnd.course1.cloudstorage.TabServices.NoteServices;
import com.udacity.jwdnd.course1.cloudstorage.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {
    private  UserServices userService;
    private  NoteServices noteService;

    @Autowired
    public NoteController(UserServices userService, NoteServices noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }



    @GetMapping
    public String getHomePage(
            org.springframework.security.core.Authentication authentication, Model model) {
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", this.noteService.getNoteListings(userId));

        return "home";
    }

    private Integer getUserId( org.springframework.security.core.Authentication authentication) {
        UserModel user = userService.getUser(authentication.getName());
        return user.getUserId();
    }

    @PostMapping("add-note")
    public String newNote(
            org.springframework.security.core.Authentication authentication,
            @ModelAttribute("newNote") NoteForm newNote,
            Model model) {

        // Prevent Note duplication
        if ( noteService.getExistingTitles(newNote.getTitle()).isEmpty() ) {

            model.addAttribute("notes", noteService.getNoteListings(getUserId(authentication)));
            model.addAttribute("result", "success");
        }else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "the note is duplicated.");
        }

        if (!newNote.getNoteId().isEmpty()) {
            // Update note method if note id is existed
            NoteModel note = getNote(Integer.parseInt(newNote.getNoteId()));
            noteService.updateNote(note.getNoteId(), newNote.getTitle(), newNote.getDescription());
        } else if (newNote.getNoteId().isEmpty()&&noteService.getExistingTitles(newNote.getTitle()).isEmpty()) {
// create new not if there is no same id
            noteService.createNote(newNote.getTitle(), newNote.getDescription(), authentication.getName());

        }


        return "result";
    }

// Delete Note method
    @GetMapping(value = "/deletenote/{noteId}")
    public String deleteNote(org.springframework.security.core.Authentication authentication, @PathVariable Integer noteId, Model model) {
        noteService.deleteNote(noteId);

        // Adding Attribute to template pages.

        model.addAttribute("notes", noteService.getNoteListings(getUserId(authentication)));
        model.addAttribute("result", "success");

        return "result";
    }


    // getting note by ID
    @GetMapping(value = "/get-note/{noteId}")
    public NoteModel getNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }



}
