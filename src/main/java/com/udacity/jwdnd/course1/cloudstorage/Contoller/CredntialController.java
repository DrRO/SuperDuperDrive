package com.udacity.jwdnd.course1.cloudstorage.Contoller;

import com.udacity.jwdnd.course1.cloudstorage.Forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.Models.*;
import com.udacity.jwdnd.course1.cloudstorage.TabServices.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredntialController {
    private  UserServices userService;
    private  CredentialServices credentialService;
    private  EncryptionService encryptionService;


    public CredntialController(UserServices userService, CredentialServices credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(
            org.springframework.security.core.Authentication authentication
            , Model model) {

        UserModel user = userService.getUser(authentication.getName());
        model.addAttribute("credentials", this.credentialService.getCredentialList(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @PostMapping("add-credential")
    public String newCredential(org.springframework.security.core.Authentication authentication, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        //Add random data which combined with the input string when hashing
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedKey = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(newCredential.getPassword(), encodedKey);
// Add attributes to home.html
        UserModel user = userService.getUser(authentication.getName());
        model.addAttribute("credentials", credentialService.getCredentialList(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

// check if credential id is exist to enable new credit editing
        if (!newCredential.getCredentialId().isEmpty()) {
            CredentialModel existingCredential = getCredential(Integer.parseInt(newCredential.getCredentialId()));
            credentialService.updateCredential(existingCredential.getCredentialid(), newCredential.getUserName(), newCredential.getUrl(), encodedKey, encryptedPassword);


        } else {
            credentialService.newCredential(newCredential.getUrl(), authentication.getName(), newCredential.getUserName(), encodedKey, encryptedPassword);

        }


        return "result";
    }
// getting Credential by ID
    @GetMapping(value = "/get-credential/{credentialId}")
    public CredentialModel getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }
//Delete Credential
    @GetMapping(value = "/deletecredential/{credentialId}")
    public String deleteCredential(
            org.springframework.security.core.Authentication authentication, @PathVariable Integer credentialId, Model model) {
        credentialService.deleteCredential(credentialId);

        UserModel user = userService.getUser(authentication.getName());
        model.addAttribute("credentials", credentialService.getCredentialList(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }
}
