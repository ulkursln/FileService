package com.springboot.rest.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.dto.model.FileDTO;


public interface StorageService {

    void init();

    String store(MultipartFile file);

    void upload(MultipartFile file);

    FileDTO findFirstByOrderByCreatedDateDesc();

    void deleteAll();

    FileDTO getRandomFile();

    String getLongestTwentyLinesOfFile();
}
