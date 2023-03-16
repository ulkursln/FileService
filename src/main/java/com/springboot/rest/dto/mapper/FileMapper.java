package com.springboot.rest.dto.mapper;

import com.springboot.rest.dto.model.FileDTO;
import com.springboot.rest.model.FileEntity;

public class FileMapper {
    public static FileDTO toFileDTO(FileEntity fileEntity) {

        return new FileDTO()
                .setFileName(fileEntity.getFileName())
                .setFilePath(fileEntity.getFilePath())
                .setTwentyLongestLine(null);


    }
}
