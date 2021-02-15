package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {

    @GetMapping("/result")
    public String resultView (Authentication authentication, Model model) {
        return "result";
    }
}
