package com.springboot.rest.utility;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import com.springboot.rest.exception.FileNotFoundException;

public class FileUploadHelperTest {

    private final static String TEST_FILE_PATH = "test.txt";

    @BeforeEach
    void setup() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        FileWriter writer = new FileWriter(testFile);
        writer.write("line 1\n");
        writer.write("line 2\n");
        writer.write("line 3\n");
        writer.write("line 4\n");
        writer.write("line 5\n");
        writer.write("line 6\n");
        writer.write("line 7\n");
        writer.close();
    }

    @AfterAll
    public static void tearDown(){
        File testFile = new File(TEST_FILE_PATH);
        testFile.delete();
    }

    @Test
    void testRandomLineReturnsNotNull() throws FileNotFoundException {
        // Given
        String path = TEST_FILE_PATH;

        // When
        Pair<String, Long> result = FileUploadHelper.randomLine(path);

        // Then
        Assertions.assertNotNull(result.getFirst());
    }

    @Test
    void testRandomLineReturnsLineNumberGreaterThanZero() throws FileNotFoundException {
        // Given
        String path = TEST_FILE_PATH;

        // When
        Pair<String, Long> result = FileUploadHelper.randomLine(path);

        // Then
        Assertions.assertTrue(result.getSecond() > 0);
    }

    @Test
    void testRandomLineThrowsFileNotFoundExceptionWhenFileDoesNotExist() {
        // Given
        String path = "path/does/not/exist.txt";

        // When/Then
        Assertions.assertThrows(FileNotFoundException.class, () -> FileUploadHelper.randomLine(path));
    }
}


