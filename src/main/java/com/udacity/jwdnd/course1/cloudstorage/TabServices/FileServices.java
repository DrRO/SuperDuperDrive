package com.udacity.jwdnd.course1.cloudstorage.TabServices;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.Models.NoteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileServices {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileServices(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }


    // upload New file
    public void uploadFile(MultipartFile multipartFile, String userName) throws IOException {

        int read;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        InputStream inputStream = multipartFile.getInputStream();

        while ((read = inputStream.read(new byte[1024], 0, new byte[1024].length)) != -1) {
            buffer.write(new byte[1024], 0, read);
        }
        buffer.flush();

        FileModel file = new FileModel(
                0,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                String.valueOf(multipartFile.getSize()),
                userMapper.getUser(userName).getUserId(),
                buffer.toByteArray()
        );
        fileMapper.insertFile(file);


    }

    //Get Note List according to file name  from NoteMapper
    public List<FileModel> getExistingFiless(String fileName) {

        return fileMapper.getExistingFiless(fileName);
    }

    //Get File info from FileMapper
    public String[] getFileList(Integer userId) {
        return fileMapper.getFileList(userId);
    }
    public FileModel getFile(String fileName) {
        return fileMapper.getFileName(fileName);
    }

    public void deleteFile(String fileName) {

        fileMapper.deleteFile(fileName);
    }
}
