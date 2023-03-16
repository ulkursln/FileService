package com.springboot.rest.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RandomLineDTO {
    private long lineNumber;

    private String fileName;

    private char mostFrequentLetter;

    private String randomLine;
}
