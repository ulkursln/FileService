package com.springboot.rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.springboot.rest.config.StorageProperties;
import com.springboot.rest.service.FileStorageService;
import com.springboot.rest.service.StorageService;

import jakarta.annotation.Resource;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FileServiceApplication {

    @Resource
    FileStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }


}
