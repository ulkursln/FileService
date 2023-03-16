package com.springboot.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.rest.model.LongestLineEntity;

public interface LongestLineRepository extends JpaRepository<LongestLineEntity, String> {

}
