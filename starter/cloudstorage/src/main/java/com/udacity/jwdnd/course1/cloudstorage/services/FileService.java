package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FileService {
    private final FileMapper fileMapper;
    public final Integer MAX_PERMITTED_SIZE = 1048576;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int createFile(File file) {
        return fileMapper.insert(new File(null, file.getFilename(), file.getContenttype(), file.getFilesize(), file.getUserid(), file.getFiledata()));
    }

    public File getFile(String filename, Integer userid) {
        return fileMapper.getFile(filename, userid);
    }

    public List<File> getFilesList(Integer userid) {
        return fileMapper.getFilesList(userid);
    }

    public List<FileForm> getFileFormsList(Integer userid) {
        List<File> filesList = this.getFilesList(userid);
        List<FileForm> result = new ArrayList<FileForm>();
        for (File file : filesList) {
            result.add(new FileForm(file.getFilename(), file.getUserid()));
        }
        return result;
    }

    public void updateFile(File file) {
        fileMapper.update(file);
    }

    public void deleteFile(String filename, Integer userid) {
        fileMapper.delete(filename, userid);
    }
}
