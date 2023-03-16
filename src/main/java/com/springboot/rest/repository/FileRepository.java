package com.springboot.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.rest.model.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {

    FileEntity findFirstByOrderByCreatedDateDesc();

    FileEntity findTwenty_Longest_LineById(Long id);

    @Query(value = "SELECT * FROM Files ORDER BY RAND() LIMIT 1", nativeQuery = true)
    FileEntity getRandomFile();
}
