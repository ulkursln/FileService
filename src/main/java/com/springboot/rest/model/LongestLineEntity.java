package com.springboot.rest.model;


import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "longest_hundred")
@Accessors(chain = true)
@Data
public class LongestLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private Long id;

    /*The maximum length of CHARACTER, CHARACTER VARYING and VARCHAR_IGNORECASE values and
    columns is 1_000_000_000 characters for H2.
    In the file table, we combine the first 20 longest column rows for each file
    and keep them in a single column. "50000000" is assigned to match the size there.
    */
    @Column(nullable = false, length = 50000000)
    private String line;


    @Column(nullable = false, length = 255)
    private String filePath;

    @Column(nullable = false)
    private int length;
}
