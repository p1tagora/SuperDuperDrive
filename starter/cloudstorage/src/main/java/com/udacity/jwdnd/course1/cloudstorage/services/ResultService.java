package com.udacity.jwdnd.course1.cloudstorage.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ResultService {
    public final String resultSuccess = "success";
    public final String resultFail = "fail";
    public final String resultEmptyFile = "emptyFile";
    public final String resultDuplicateFile = "duplicateFile";
    public final String resultFileTooLarge = "fileTooLarge";
}
