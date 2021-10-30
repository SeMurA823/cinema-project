package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.services.FileWriterService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Log4j2
public class FileWriterServiceImpl implements FileWriterService {
    @Value("${upload.default.path}")
    private String DEFAULT_PATH;

    @Override
    public File write(@NotNull MultipartFile file, File uploadPath) {
        if (!uploadPath.exists()) {
            uploadPath.mkdir();
        }
        String newFilename = generateUniqueFilename(file.getOriginalFilename());
        String newPath = uploadPath.getAbsolutePath() + '/' + newFilename;
        File newFile = new File(newPath);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            log.log(Level.WARN, e.getMessage());
            throw new RuntimeException(e);
        }
        return newFile;
    }

    @Override
    public File write(@NotNull MultipartFile file, String uploadPath) {
        return write(file, new File(uploadPath));
    }

    @Override
    public File write(@NotNull MultipartFile file) {
        return write(file, DEFAULT_PATH);
    }

    private String generateUniqueFilename(String original) {
        log.log(Level.DEBUG, "Original filename: {}", original);
        String newFilename = new StringBuilder()
                .append(UUID.randomUUID().toString().replace("-", ""))
                .append('.')
                .append(original)
                .toString();
        log.log(Level.DEBUG, "New filename: {}", newFilename);
        return newFilename;
    }
}
