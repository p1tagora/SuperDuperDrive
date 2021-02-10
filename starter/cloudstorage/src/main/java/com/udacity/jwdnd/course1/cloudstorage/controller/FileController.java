package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/home/file-upload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) {
        if (fileUpload.isEmpty()) {
            //TODO - error handling
            return "redirect:/home";
        }
        String filename = fileUpload.getOriginalFilename();
        Integer userid = this.userService.getUser(authentication.getName()).getUserid();
        if (this.fileService.getFile(filename, userid) != null) {
            //TODO - error handling
            return "redirect:/home";
        }

        File file = new File();
        file.setFilename(filename);
        file.setContenttype(fileUpload.getContentType());
        file.setFilesize(fileUpload.getSize());
        file.setUserid(userid);
        try {
            file.setFiledata(fileUpload.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fileService.createFile(file);
        model.addAttribute("filesList", this.fileService.getFileFormsList(file.getUserid()));
        return "redirect:/home";
    }

    @GetMapping(value="/home/file-view")
    public void viewFile(Authentication authentication, @RequestParam("filename") String filename, HttpServletResponse response) {
        File file = this.fileService.getFile(filename, this.userService.getUser(authentication.getName()).getUserid());
        response.setContentType(file.getContenttype());
        response.setContentLengthLong(file.getFilesize());
        response.setHeader("File name: ", file.getFilename());
        try {
            ServletOutputStream stream = response.getOutputStream();
            stream.write(file.getFiledata());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/home/file-delete")
    public String deleteFile(Authentication authentication, @RequestParam("filename") String filename, Model model) {
        Integer userid = this.userService.getUser(authentication.getName()).getUserid();
        this.fileService.deleteFile(filename, userid);
        model.addAttribute("filesList", this.fileService.getFileFormsList(userid));
        return "redirect:/home";
    }
}
