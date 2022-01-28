package com.udacity.jwdnd.course1.cloudstorage.Forms;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileForm {
    private MultipartFile file;

    public FileForm(MultipartFile file) {
        this.file = file;
    }
    // Generate FileForm Setter and Getter methods
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
