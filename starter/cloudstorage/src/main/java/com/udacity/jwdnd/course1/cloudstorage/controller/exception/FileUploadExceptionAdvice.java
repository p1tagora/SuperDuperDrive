package com.udacity.jwdnd.course1.cloudstorage.controller.exception;

import com.udacity.jwdnd.course1.cloudstorage.services.ResultService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadExceptionAdvice {
    private final ResultService resultService;

    public FileUploadExceptionAdvice(ResultService resultService) {
        this.resultService = resultService;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        model.addAttribute("status", this.resultService.resultFileTooLarge);
        return "result";
    }
}
