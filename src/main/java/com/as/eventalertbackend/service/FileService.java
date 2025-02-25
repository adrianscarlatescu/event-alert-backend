package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.enums.ImageTypeCode;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
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

@Service
@Slf4j
public class FileService {

    private final AppProperties appProperties;

    @Autowired
    public FileService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public boolean imageExists(String imagePath) {
        String mediaPath = appProperties.getMediaDirectoryPath();
        if (!imagePath.startsWith(mediaPath)) {
            throw new StorageFailException(ApiErrorMessage.INVALID_IMAGE_PATH);
        }

        ClassPathResource mediaResource = new ClassPathResource(imagePath);
        File imageFile;
        try {
            imageFile = mediaResource.getFile();
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_RETRIEVE_FAIL);
        }

        return imageFile.exists() && !imageFile.isDirectory();
    }

    public Resource readImage(String imagePath) {
        ClassPathResource imageResource = new ClassPathResource(imagePath);
        try {
            return new UrlResource(imageResource.getURI());
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_RETRIEVE_FAIL);
        }
    }

    public String writeImage(ImageTypeCode imageTypeCode, MultipartFile file) {
        String mediaPath = appProperties.getMediaDirectoryPath();

        if (file.getSize() == 0) {
            throw new InvalidActionException(ApiErrorMessage.IMAGE_MANDATORY);
        }

        String fileOriginalName = file.getOriginalFilename();
        if (fileOriginalName == null) {
            throw new InvalidActionException(ApiErrorMessage.IMAGE_NAME_MANDATORY);
        }

        int extensionStartIndex = fileOriginalName.lastIndexOf(".") + 1;
        String extension = fileOriginalName.substring(extensionStartIndex);
        String imageTypeName = imageTypeCode.name().toLowerCase();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String imageDirectoryPath = mediaPath + imageTypeName;
        String imageName = imageTypeName + "_" + timestamp + "." + extension;

        String imagePath = imageDirectoryPath + "/" + imageName;

        ClassPathResource imageResource = new ClassPathResource(imageDirectoryPath);
        log.info("Begin image write request");

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(imageResource.getFile().getPath(), imageName);
            Files.write(path, bytes);
            log.info("Image successfully stored: {}", imagePath);
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_STORE_FAIL);
        }

        return imagePath;
    }

}
