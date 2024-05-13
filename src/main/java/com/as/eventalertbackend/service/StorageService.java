package com.as.eventalertbackend.service;

import com.as.eventalertbackend.handler.exception.StorageFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class StorageService {

    public static final String IMG_PATH = "img/";
    public static final String SERVER_PATH = "C:/Users/ADRIAN/IdeaProjects/event-alert-backend/src/main/resources/";

    public Resource readImage(String imgRelativePath) {
        Path path = Paths.get(SERVER_PATH + imgRelativePath);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new StorageFailException(
                    "Could not read the image " + imgRelativePath,
                    "Could not retrieve the image");
        }
    }

    public String writeImage(MultipartFile file) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String name = file.getOriginalFilename() + dateTime.format(formatter) + ".jpg";

        String serverImgPath = SERVER_PATH + IMG_PATH;
        log.info("Begin image write request, directory: {}", serverImgPath);

        File directory = new File(serverImgPath);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdir();
            if (!isCreated) {
                throw new StorageFailException(
                        "Could not create the directory " + directory.getAbsolutePath(),
                        "Could not store the image");

            }
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(serverImgPath + name);
            Files.write(path, bytes);
            log.info("Image write successful: {}", name);
        } catch (IOException e) {
            throw new StorageFailException(
                    "Could not write the image " + file.getOriginalFilename(),
                    "Could not store the image");
        }

        return IMG_PATH + name;
    }

}
