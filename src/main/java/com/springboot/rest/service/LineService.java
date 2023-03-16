package com.springboot.rest.service;

import java.util.List;

import com.springboot.rest.dto.model.LongestLineDTO;

public interface LineService {

    List<String> loadAllLinesString();

    List<LongestLineDTO> loadAll();

    void deleteAll();

    void saveAll(List<LongestLineDTO> items);

}
