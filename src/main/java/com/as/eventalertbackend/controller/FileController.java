package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> singleImageDownload(@RequestParam("path") String imagePath) {
        Resource resource = fileService.readImage(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/image")
    public ResponseEntity<String> singleImageUpload(@RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok("\"" + fileService.writeImage(image) + "\"");
    }

}
