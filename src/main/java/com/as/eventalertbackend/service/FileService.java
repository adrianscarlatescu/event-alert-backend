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
        File mediaFile;
        try {
            mediaFile = mediaResource.getFile();
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_RETRIEVE_FAIL);
        }

        return mediaFile.exists() && !mediaFile.isDirectory();
    }

    public Resource readImage(String imagePath) {
        ClassPathResource imageResource = new ClassPathResource(imagePath);
        try {
            return new UrlResource(imageResource.getURI());
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_RETRIEVE_FAIL);
        }
    }

    public String writeImage(ImageTypeCode imageTypeCode, String suffix, MultipartFile file) {
        String mediaPath = appProperties.getMediaDirectoryPath();

        if (file.getSize() == 0) {
            throw new InvalidActionException(ApiErrorMessage.IMAGE_MANDATORY);
        }
        if (file.getOriginalFilename() == null) {
            throw new InvalidActionException(ApiErrorMessage.IMAGE_NAME_MANDATORY);
        }

        switch (imageTypeCode) {
            case USER, EVENT -> {
                if (!file.getOriginalFilename().endsWith(".jpg")) {
                    throw new InvalidActionException(ApiErrorMessage.INVALID_IMAGE_EXTENSION);
                }
            }
            case CATEGORY, TYPE_HUMAN_MADE, TYPE_NATURAL, TYPE_OTHER -> {
                if (!file.getOriginalFilename().endsWith(".png")) {
                    throw new InvalidActionException(ApiErrorMessage.INVALID_IMAGE_EXTENSION);
                }
            }
        }

        String imageDirectoryPath = switch (imageTypeCode) {
            case USER -> mediaPath + "user/";
            case EVENT -> mediaPath + "event/";
            case CATEGORY -> mediaPath + "category/";
            case TYPE_HUMAN_MADE -> mediaPath + "type/human-made/";
            case TYPE_NATURAL -> mediaPath + "type/natural/";
            case TYPE_OTHER -> mediaPath + "type/other/";
        };

        String imageFileName = switch (imageTypeCode) {
            case USER -> "user_" + suffix + ".jpg";
            case EVENT -> "event_" + suffix + ".jpg";
            case CATEGORY -> "category_" + suffix + ".png";
            case TYPE_HUMAN_MADE, TYPE_NATURAL, TYPE_OTHER -> "type_" + suffix + ".png";
        };

        String imageFilePath = imageDirectoryPath + imageFileName;

        ClassPathResource imageResource = new ClassPathResource(imageFilePath);
        log.info("Begin image write request");

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(imageResource.getFile().getPath());
            Files.write(path, bytes);
            log.info("Image successfully stored: {}", imageFilePath);
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_STORE_FAIL);
        }

        return imageFilePath;
    }

}
