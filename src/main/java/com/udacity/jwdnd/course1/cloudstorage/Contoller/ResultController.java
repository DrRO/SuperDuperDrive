package com.udacity.jwdnd.course1.cloudstorage.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller

public class ResultController {

    @RequestMapping("/result")
    public String resultView(){
        return "result";
    }
}



