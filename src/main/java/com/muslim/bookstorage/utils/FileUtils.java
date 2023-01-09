package com.muslim.bookstorage.utils;

import com.muslim.bookstorage.exception.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class FileUtils {
    public static String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyFileException("Empty file");
        }
        try {
            Path path = Path.of("books/" + UUID.randomUUID() + ".pdf");
            Files.createFile(path);
            Files.write(path, file.getBytes());

            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
