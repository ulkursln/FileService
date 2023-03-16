package com.springboot.rest.dto.mapper;


import com.springboot.rest.dto.model.LongestLineDTO;
import com.springboot.rest.model.LongestLineEntity;

public class LongestLineMapper {

    public static LongestLineDTO toLongestLineDTO(LongestLineEntity longestLineEntity) {

        return new LongestLineDTO()
                .setLine(longestLineEntity.getLine())
                .setFilePath(longestLineEntity.getFilePath())
                .setLength(longestLineEntity.getLength());
    }

    public static LongestLineEntity toLongestLineEntity(LongestLineDTO longestLineDTO) {

        return new LongestLineEntity()
                .setLine(longestLineDTO.getLine())
                .setFilePath(longestLineDTO.getFilePath())
                .setLength(longestLineDTO.getLength());
    }
}
