package com.springboot.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.dto.model.FileDTO;
import com.springboot.rest.dto.model.RandomLineDTO;
import com.springboot.rest.service.StorageService;
import com.springboot.rest.utility.FileUploadHelper;

@RestController
@RequestMapping(path="/fileService")
@CrossOrigin(origins="*")
public class RandomLineController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/random-line")
    public ResponseEntity<?> getRandomLine(@RequestHeader("Accept") String acceptHeader) {

        FileDTO file = storageService.findFirstByOrderByCreatedDateDesc();
        Pair<String, Long> pair = FileUploadHelper.randomLine(file.getFilePath());

        String randomLine = pair.getFirst();

        if (acceptHeader.contains("application/*")) {
            char mostFrequentChar = FileUploadHelper.getMostFrequentChar(pair.getFirst());
            long lineNumber = pair.getSecond();
            String fileName = file.getFileName();
            RandomLineDTO randomLineDTO = new RandomLineDTO(lineNumber, fileName, mostFrequentChar, randomLine);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(randomLineDTO);
        }

        if (acceptHeader.contains("application/json")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(randomLine);

        } else if (acceptHeader.contains("application/xml")) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(randomLine);

        } else {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(randomLine);
        }
    }

    @GetMapping("/random-line-backwards")
    public ResponseEntity<?> getRandomLineBackwards() {

        FileDTO file = storageService.getRandomFile();

        Pair<String, Long> pair = FileUploadHelper.randomLine(file.getFilePath());

        String randomLine = pair.getFirst();
        StringBuilder sb = new StringBuilder(randomLine);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(sb.reverse().toString());
    }
}
