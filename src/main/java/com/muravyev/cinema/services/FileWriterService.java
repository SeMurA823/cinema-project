package com.muravyev.cinema.services;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;

public interface FileWriterService {
    File write(@NotNull MultipartFile file, File uploadPath);

    File write(@NotNull MultipartFile file, String uploadPath);

    File write(@NotNull MultipartFile file);

    boolean remove(File file);

    boolean remove(String filename);
}
