package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.StorageFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
@Slf4j
public class FileService {

    private final AppProperties appProperties;

    @Autowired
    public FileService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public boolean imageExists(String imagePath) {
        String imagesPath = appProperties.getImagesDirectoryPath();
        ClassPathResource imagesResource = new ClassPathResource(imagesPath);

        File imagesDirectory;
        try {
            imagesDirectory = imagesResource.getFile();
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.FILE_LIST_FAIL);
        }

        String[] filesNames = imagesDirectory.list();
        if (filesNames == null) {
            throw new StorageFailException(ApiErrorMessage.FILE_LIST_FAIL);
        }

        if (!imagePath.startsWith(imagesPath)) {
            throw new StorageFailException(ApiErrorMessage.INVALID_IMAGE_NAME);
        }

        String imageName = imagePath.substring(imagesPath.length());
        return Arrays.asList(filesNames).contains(imageName);
    }

    public Resource readImage(String imagePath) {
        ClassPathResource imageResource = new ClassPathResource(imagePath);
        try {
            return new UrlResource(imageResource.getURI());
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_RETRIEVE_FAIL);
        }
    }

    public String writeImage(MultipartFile file) {
        String imagesPath = appProperties.getImagesDirectoryPath();

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String imageName = file.getOriginalFilename() + dateTime.format(formatter) + ".jpg";

        ClassPathResource imagesResource = new ClassPathResource(imagesPath);
        log.info("Begin image write request");

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(imagesResource.getFile().getPath(), imageName);
            Files.write(path, bytes);
            log.info("Image successfully stored: {}", imageName);
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_STORE_FAIL);
        }

        return imagesPath + imageName;
    }

}
