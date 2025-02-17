package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.enums.ImageTypeCode;
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
        ClassPathResource mediaResource = new ClassPathResource(mediaPath);

        File mediaDirectory;
        try {
            mediaDirectory = mediaResource.getFile();
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.FILE_LIST_FAIL);
        }

        String filePath = mediaDirectory.getAbsolutePath().replace(mediaPath, "").concat(imagePath);
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
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

        String imagePath =  switch (imageTypeCode) {
            case USER -> mediaPath + "/user";
            case EVENT -> mediaPath + "/event";
            case CATEGORY -> mediaPath + "/category";
            case TYPE_HUMAN_MADE -> mediaPath + "/type/human-made";
            case TYPE_NATURAL -> mediaPath + "/type/natural";
            case TYPE_OTHER -> mediaPath + "/type/other";
        };

        String imageName = switch (imageTypeCode) {
            case USER -> "user_" + suffix;
            case EVENT -> "event_" + suffix;
            case CATEGORY -> "category_" + suffix;
            case TYPE_HUMAN_MADE, TYPE_NATURAL, TYPE_OTHER -> "type_" + suffix;
        };

        String imageNameWithExtension = imageName + ".jpg";

        ClassPathResource imageResource = new ClassPathResource(imagePath);
        log.info("Begin image write request");

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(imageResource.getFile().getPath(), imageNameWithExtension);
            Files.write(path, bytes);
            log.info("Image successfully stored: {}", imageNameWithExtension);
        } catch (IOException e) {
            throw new StorageFailException(ApiErrorMessage.IMAGE_STORE_FAIL);
        }

        return mediaPath + imageName;
    }

}
