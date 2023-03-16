package com.springboot.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.service.StorageService;


@RestController
@RequestMapping(path="/fileService")
@CrossOrigin(origins="*")
public class FileUploadController {

    @Autowired
    private StorageService storageService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        storageService.upload(file);

        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(message);

    }
}
