package com.udacity.jwdnd.course1.cloudstorage.Contoller;


import com.udacity.jwdnd.course1.cloudstorage.Models.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserServices userService;

    public SignupController(UserServices userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String signupView() {
        return "signup";
    }




    @PostMapping
    public String signupUser(@ModelAttribute UserModel user, RedirectAttributes redirectAttributes){


        //Validate Signup methods
        String signupError = null;
        if(!userService.checkUserAvaliability(user.getUsername())){
            signupError = "This user already exist";

        }
        if(signupError == null){
            int rowAdded = userService.NewUserAcount(user);
            if(rowAdded < 0){
                signupError = "Error signing you up. Please try again";
            }

            if(signupError == null){
// Enabling Redirect to login page after Regestration
                redirectAttributes.addFlashAttribute("Success", true);
                return "redirect:/login";
            }
        }
        redirectAttributes.addFlashAttribute("Error",signupError);
        return "redirect:/signup";

    }
}
