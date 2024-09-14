package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> singleImageDownload(@RequestParam("path") String imgPath) {
        Resource resource = storageService.readImage(imgPath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/image")
    public ResponseEntity<String> singleImageUpload(@RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok("\"" + storageService.writeImage(image) + "\"");
    }

}
