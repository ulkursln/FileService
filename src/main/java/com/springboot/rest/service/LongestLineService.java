package com.springboot.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest.dto.mapper.LongestLineMapper;
import com.springboot.rest.dto.model.LongestLineDTO;
import com.springboot.rest.exception.DeleteFromDBException;
import com.springboot.rest.exception.SaveToDBException;
import com.springboot.rest.model.LongestLineEntity;
import com.springboot.rest.repository.LongestLineRepository;

@Service
public class LongestLineService implements LineService {

    @Autowired
    private LongestLineRepository longestLineRepository;

    @Override
    public List<String> loadAllLinesString() {

        List<LongestLineEntity> longestLineEntities = longestLineRepository.findAll();
        if (!longestLineEntities.isEmpty()) {
            return longestLineEntities
                    .stream()
                    .map(line -> LongestLineMapper.toLongestLineDTO(line).getLine())
                    .collect(Collectors.toList());

        }
        return new ArrayList<>();
    }

    @Override
    public List<LongestLineDTO> loadAll() {

        List<LongestLineEntity> longestLineEntities = longestLineRepository.findAll();
        if (!longestLineEntities.isEmpty()) {
            return longestLineEntities
                    .stream()
                    .map(line -> LongestLineMapper.toLongestLineDTO(line))
                    .collect(Collectors.toList());

        }
        return new ArrayList<>();
    }

    @Override
    public void deleteAll() {
        try {
            longestLineRepository.deleteAll();
        } catch (Exception e) {
            throw new DeleteFromDBException();
        }
    }

    @Override
    public void saveAll(List<LongestLineDTO> items) {
        try {
            List<LongestLineEntity> entityList = items
                    .stream()
                    .map(line -> LongestLineMapper.toLongestLineEntity(line))
                    .collect(Collectors.toList());


            longestLineRepository.saveAll(entityList);

        } catch (Exception e) {
            throw new SaveToDBException();
        }
    }
}
