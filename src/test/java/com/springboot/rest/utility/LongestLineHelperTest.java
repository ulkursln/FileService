package com.springboot.rest.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.springboot.rest.dto.model.LongestLineDTO;

public class LongestLineHelperTest {

    private final static String TEST_FILE_PATH = "test.txt";

    @BeforeEach
    void setup() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        FileWriter writer = new FileWriter(testFile);
        writer.write("line 1 1\n");
        writer.write("line 2 11\n");
        writer.write("line 3 111\n");
        writer.write("line 4 1111\n");
        writer.write("line 5 11111\n");
        writer.write("line 6 111111\n");
        writer.write("line 7 1111111\n");
        writer.close();
    }

    @AfterAll
    public static void tearDown(){
        File testFile = new File(TEST_FILE_PATH);
        testFile.delete();
    }

    @Test
    public void testFillPriorityQueue() {
        LongestLineDTO dto1 = new LongestLineDTO("abc", "file1", 3);
        LongestLineDTO dto2 = new LongestLineDTO("defg", "file2", 4);
        LongestLineDTO dto3 = new LongestLineDTO("hijklmn", "file3", 7);
        List<LongestLineDTO> list = new ArrayList<>();
        list.add(dto1);
        list.add(dto2);
        list.add(dto3);
        PriorityQueue<LongestLineDTO> pq = LongestLineHelper.fillPriorityQueue(list);
        Assertions.assertEquals(dto1, pq.poll());
        Assertions.assertEquals(dto2, pq.poll());
        Assertions.assertEquals(dto3, pq.poll());
    }

    @Test
    public void testGetLongest100LinesFromFiles() {
        LongestLineDTO dto1 = new LongestLineDTO("abc", "file1", 3);
        LongestLineDTO dto2 = new LongestLineDTO("defg", "file2", 4);
        LongestLineDTO dto3 = new LongestLineDTO("hijklmn", "file3", 7);
        LongestLineDTO dto4 = new LongestLineDTO("opqrs", "file4", 5);
        PriorityQueue<LongestLineDTO> existingLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        existingLines.offer(dto1);
        existingLines.offer(dto2);
        existingLines.offer(dto3);
        PriorityQueue<LongestLineDTO> newLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        newLines.offer(dto4);
        List<LongestLineDTO> result = LongestLineHelper.getLongest100LinesFromFiles(existingLines, newLines);
        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals(dto1, result.get(0));
        Assertions.assertEquals(dto2, result.get(1));
        Assertions.assertEquals(dto4, result.get(2));
        Assertions.assertEquals(dto3, result.get(3));
    }

    @Test
    public void testLongest100LinesListDoesNotExeed100() {
        List<LongestLineDTO> existingLineData = LineGenerator.generateLine(90);
        List<LongestLineDTO> newLineData = LineGenerator.generateLine(90);
        PriorityQueue<LongestLineDTO> existingLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        existingLineData.forEach(item ->
                existingLines.offer(item)
        );
        PriorityQueue<LongestLineDTO> newLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        newLineData.forEach(item ->
                newLines.offer(item)
        );
        List<LongestLineDTO> result = LongestLineHelper.getLongest100LinesFromFiles(existingLines, newLines);
        Assertions.assertEquals(100, result.size());
    }

    @Test
    public void testLongest100LinesListWhenThereIsLongerNewLine() {
        List<LongestLineDTO> existingLineData = LineGenerator.generateLine(99);
        String longestStr = "11111111111111111111111111111111111111111111111111";
        LongestLineDTO longestLineDTO = new LongestLineDTO(longestStr, "file", longestStr.length());
        PriorityQueue<LongestLineDTO> existingLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        existingLineData.forEach(item ->
                existingLines.offer(item)
        );
        PriorityQueue<LongestLineDTO> newLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        newLines.offer(longestLineDTO);

        List<LongestLineDTO> result = LongestLineHelper.getLongest100LinesFromFiles(existingLines, newLines);
        Assertions.assertEquals(100, result.size());
        Assertions.assertEquals(result.get(99), longestLineDTO);
    }

    @Test
    public void testLongest100LinesListWhenThereIsShorterNewLineThenItIsNotAdded() {
        List<LongestLineDTO> existingLineData = LineGenerator.generateLine(100);
        String shortestStr = "1";
        LongestLineDTO shortestLineDTO = new LongestLineDTO(shortestStr, "file", shortestStr.length());
        PriorityQueue<LongestLineDTO> existingLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        existingLineData.forEach(item ->
                existingLines.offer(item)
        );
        PriorityQueue<LongestLineDTO> newLines = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        newLines.offer(shortestLineDTO);

        List<LongestLineDTO> result = LongestLineHelper.getLongest100LinesFromFiles(existingLines, newLines);
        Assertions.assertNotEquals(result.get(0), shortestLineDTO);
        Assertions.assertTrue(result.get(0).getLength() >= 3);
    }


    @Test
    public void testGetPriorityQueue() {
        PriorityQueue<LongestLineDTO> pq = LongestLineHelper.getPriorityQueue(3, TEST_FILE_PATH);
        Assertions.assertEquals(3, pq.size());
        LongestLineDTO dto1 = pq.poll();
        Assertions.assertEquals("line 5 11111", dto1.getLine());
        LongestLineDTO dto2 = pq.poll();
        Assertions.assertEquals("line 6 111111", dto2.getLine());
        LongestLineDTO dto3 = pq.poll();
        Assertions.assertEquals("line 7 1111111", dto3.getLine());
    }


    @Test
    public void testGetLongest20LinesFromFile() {
        PriorityQueue<LongestLineDTO> pq = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());

        // add some longest line DTOs to the priority queue
        pq.add(new LongestLineDTO("This is the longest line 1", "file", 30));
        pq.add(new LongestLineDTO("This is the longest line 2", "file", 29));
        pq.add(new LongestLineDTO("This is the longest line 3", "file", 28));
        pq.add(new LongestLineDTO("This is the longest line 4", "file", 27));
        pq.add(new LongestLineDTO("This is the longest line 5", "file", 26));
        pq.add(new LongestLineDTO("This is the longest line 6", "file", 25));
        pq.add(new LongestLineDTO("This is the longest line 7", "file", 24));
        pq.add(new LongestLineDTO("This is the longest line 8", "file", 23));
        pq.add(new LongestLineDTO("This is the longest line 9", "file", 22));
        pq.add(new LongestLineDTO("This is the longest line 10", "file", 21));
        pq.add(new LongestLineDTO("This is the longest line 11", "file", 20));
        pq.add(new LongestLineDTO("This is the longest line 12", "file", 19));
        pq.add(new LongestLineDTO("This is the longest line 13", "file", 18));
        pq.add(new LongestLineDTO("This is the longest line 14", "file", 17));
        pq.add(new LongestLineDTO("This is the longest line 15", "file", 16));
        pq.add(new LongestLineDTO("This is the longest line 16", "file", 15));
        pq.add(new LongestLineDTO("This is the longest line 17", "file", 14));
        pq.add(new LongestLineDTO("This is the longest line 18", "file", 13));
        pq.add(new LongestLineDTO("This is the longest line 19", "file", 12));
        pq.add(new LongestLineDTO("This is the longest line 20", "file", 11));
        pq.add(new LongestLineDTO("This is the longest line 21", "file", 10));

        String expectedOutput = "This is the longest line 1\n" +
                "This is the longest line 2\n" +
                "This is the longest line 3\n" +
                "This is the longest line 4\n" +
                "This is the longest line 5\n" +
                "This is the longest line 6\n" +
                "This is the longest line 7\n" +
                "This is the longest line 8\n" +
                "This is the longest line 9\n" +
                "This is the longest line 10\n" +
                "This is the longest line 11\n" +
                "This is the longest line 12\n" +
                "This is the longest line 13\n" +
                "This is the longest line 14\n" +
                "This is the longest line 15\n" +
                "This is the longest line 16\n" +
                "This is the longest line 17\n" +
                "This is the longest line 18\n" +
                "This is the longest line 19\n" +
                "This is the longest line 20\n";

        Assertions.assertEquals(expectedOutput, LongestLineHelper.getLongest20LinesFromFile(pq));
    }
}

