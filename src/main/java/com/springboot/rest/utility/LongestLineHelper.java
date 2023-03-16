package com.springboot.rest.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.springboot.rest.dto.model.LongestLineDTO;
import com.springboot.rest.exception.FileNotFoundException;

public class LongestLineHelper {

    public static PriorityQueue<LongestLineDTO> fillPriorityQueue(List<LongestLineDTO> listLongestLineDTO) {
        PriorityQueue<LongestLineDTO> pq = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        for (LongestLineDTO item : listLongestLineDTO)
            pq.offer(item);
        return pq;
    }

    public static List<LongestLineDTO> getLongest100LinesFromFiles(PriorityQueue<LongestLineDTO> existingLines,PriorityQueue<LongestLineDTO> newLines ) {
        existingLines.addAll(newLines);
        while(existingLines.size() > 100){
            existingLines.poll();
        }
        List<LongestLineDTO> sortedList = new ArrayList<>();
        while(!existingLines.isEmpty()){
            sortedList.add(existingLines.poll());
        }
        return sortedList;
    }

    public static PriorityQueue<LongestLineDTO> getPriorityQueue(int numLines,String filePath) {
        PriorityQueue<LongestLineDTO> pq = new PriorityQueue<>((a, b) -> a.getLength() - b.getLength());
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (pq.size() < numLines || pq.peek().getLength() < line.length()) {
                    if (line.length() > 0) {
                        pq.offer(new LongestLineDTO(line, filePath, line.length()));
                    }
                    if (pq.size() > numLines) {
                        pq.poll();
                    }
                }
            }
        }
        catch (IOException ex){
            throw new FileNotFoundException(filePath);
        }
        return pq;
    }

    public static String getLongest20LinesFromFile(PriorityQueue<LongestLineDTO> pq) {
        PriorityQueue<LongestLineDTO> copy = new PriorityQueue<>(pq.comparator().reversed());
        copy.addAll(pq);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while( i < 20 && !copy.isEmpty()){
            LongestLineDTO item = copy.poll();
            sb.append(item.getLine()).append("\n");
            i++;
        }
        return sb.toString();
    }
}
