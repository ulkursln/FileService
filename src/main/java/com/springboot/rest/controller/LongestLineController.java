package com.springboot.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.service.LineService;
import com.springboot.rest.service.StorageService;

@RestController
@RequestMapping(path="/fileService")
@CrossOrigin(origins="*")
public class LongestLineController {

    @Autowired
    private LineService lineService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/longest-100")
    public ResponseEntity<?> getLongestHundredLines() {

        List<String> list = lineService.loadAllLinesString();
      
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(list.stream().collect(Collectors.joining("\n")));

    }

    @GetMapping("/longest-20")
    public ResponseEntity<?> getLongestTwentyLinesOfFile() {

        String twentyLongestLine = storageService.getLongestTwentyLinesOfFile();
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(twentyLongestLine);

    }
}
