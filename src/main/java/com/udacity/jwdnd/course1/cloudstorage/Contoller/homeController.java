package com.udacity.jwdnd.course1.cloudstorage.Contoller;

import com.udacity.jwdnd.course1.cloudstorage.Forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.Forms.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.Forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.Models.*;
import com.udacity.jwdnd.course1.cloudstorage.TabServices.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.TabServices.FileServices;
import com.udacity.jwdnd.course1.cloudstorage.TabServices.NoteServices;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
@RequestMapping("/home")


public class homeController {


    private  UserServices userService;
    private FileServices fileService;
    private CredentialServices credentialService;
    private NoteServices noteService;
    private  EncryptionService encryptionService;

    public homeController(UserServices userService, FileServices fileService, CredentialServices credentialService, NoteServices noteService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(
            org.springframework.security.core.Authentication authentication,
            @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {

        // Adding @ModelAttribute annotation to allow that Spring have to add the object to our Model before asking Thymeleaf to render the template

        model.addAttribute("files", this.fileService.getFileList(getUserId(authentication)));
        model.addAttribute("notes", noteService.getNoteListings(getUserId(authentication)));
        model.addAttribute("credentials", credentialService.getCredentialList(getUserId(authentication)));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }


// Adding new File method
    @PostMapping
    public String newFile(
            org.springframework.security.core.Authentication authentication,
            @ModelAttribute("newFile") FileForm newFile,MultipartFile file,
            Model model) throws IOException {
        MultipartFile multipartFile = newFile.getFile();
        UserModel user = userService.getUser(authentication.getName());



        // Allow File Upload only in case of File not exist, The File size is allowable , The file is selected
        if (fileService.getExistingFiless(multipartFile.getOriginalFilename()).isEmpty()  && !multipartFile.isEmpty() && multipartFile.getSize() < 10000000) {

            fileService.uploadFile(multipartFile, authentication.getName());
            model.addAttribute("result", "success");


        } else if (!fileService.getExistingFiless(multipartFile.getOriginalFilename()).isEmpty()){
            model.addAttribute("result", "error");
            model.addAttribute("message", "You have tried to add a duplicate file.");
        }else if (multipartFile.isEmpty()) {
            model.addAttribute("result", "error");
            model.addAttribute("message", "Please Select file");
        }

        else if (file.getSize() > 10000000){
            model.addAttribute("result", "error");
            model.addAttribute("message", "Your file exceeds allowed size");
        }



        model.addAttribute("files", fileService.getFileList(user.getUserId()));

        return "result";
    }



//Download File when view file
    @GetMapping(value = "/view/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile(@PathVariable String fileName) {
// get the file name
        byte[] viewFile = fileService.getFile(fileName).getFileData();
        return viewFile;
    }



// Delete File

    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(org.springframework.security.core.Authentication authentication, @PathVariable String fileName,
  // Adding @ModelAttribute annotation to allow that Spring have to add the object to our Model before asking Thymeleaf to render the template
            @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        fileService.deleteFile(fileName);

        model.addAttribute("files", fileService.getFileList(getUserId(authentication)));
        model.addAttribute("result", "success");

        return "result";
    }


    private Integer getUserId(org.springframework.security.core.Authentication authentication ) {
        UserModel user = userService.getUser(authentication.getName());
        return user.getUserId();
    }


}
