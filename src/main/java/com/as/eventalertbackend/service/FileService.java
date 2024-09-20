package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.StorageFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;

@Service
@Slf4j
public class FileService {

    private final AppProperties appProperties;

    @Autowired
    public FileService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public boolean imageExists(String imageRelativePath) {
        File imagesDirectory = new File(appProperties.getStorage().getServerPath() + appProperties.getStorage().getImagesPath());
        String[] filesNames = imagesDirectory.list();
        if (filesNames == null) {
            throw new StorageFailException(ApiErrorMessage.FILE_LIST_FAIL);
        }

        if (!imageRelativePath.startsWith(appProperties.getStorage().getImagesPath())) {
            throw new StorageFailException(ApiErrorMessage.INVALID_IMAGE_NAME);
        }

        String imageName = imageRelativePath.substring(appProperties.getStorage().getImagesPath().length());
        return Arrays.asList(filesNames).contains(imageName);
    }

    public Resource readImage(String imageRelativePath) {
        Path path = Paths.get(appProperties.getStorage().getServerPath() + imageRelativePath);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_RETRIEVE_FAIL);
        }
    }

    public String writeImage(MultipartFile file) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String name = file.getOriginalFilename() + dateTime.format(formatter) + ".jpg";

        String serverImagesPath = appProperties.getStorage().getServerPath() + appProperties.getStorage().getImagesPath();
        log.info("Begin image write request, directory: {}", serverImagesPath);

        File directory = new File(serverImagesPath);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdir();
            if (!isCreated) {
                throw new StorageFailException(ApiErrorMessage.IMAGE_STORE_FAIL);
            }
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(serverImagesPath + name);
            Files.write(path, bytes);
            log.info("Image successfully stored: {}", name);
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_STORE_FAIL);
        }

        return appProperties.getStorage().getImagesPath() + name;
    }

}
