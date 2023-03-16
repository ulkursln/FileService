package com.springboot.rest.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.exception.FileNotFoundException;

public class FileUploadHelper {

    public static boolean isTextFile(String fileName) {
        Optional<String> ext = FileUploadHelper.getExtensionByStringHandling(fileName);
        if (ext != null) {
            String extension = ext.get().toLowerCase();
            if (extension.equals("txt") || extension.equals("json")  || extension.equals("xml") ||
                    extension.equals("log") || extension.equals("cfg") || extension.equals("ini") ||  
                    extension.equals("rtf") || extension.equals("md")) {
                return true;
            }
        }
        return false;
    }


    public static char getMostFrequentChar(String line) {
        char[] chars = line.toCharArray();
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : chars) {
            if (Character.isAlphabetic(c)) {
                charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            }
        }
        return charCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(' ');
    }

    /*
     * An implementation of reservoir sampling
     */
    public static Pair<String, Long> randomLine(String path) {

        String result = null;
        Random rand = new Random();
        long n = 0;
        long lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))){

        	 String line;
             while ((line = reader.readLine()) != null) {
                ++n;

                if (rand.nextLong(n) == 0) {
                    result = line;
                    lineNumber = n;
                }
            }

        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        return Pair.of(result, lineNumber);
    }

    

    public static boolean isFileEmpty(MultipartFile mfile) {

    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(mfile.getInputStream()))) {

    		String line;
            while ((line = reader.readLine()) != null) {
            	if (line != null && !line.isBlank()) {
                    return false;
                }
            }

    	} catch (IOException e) {
			throw new FileNotFoundException();
		}
        return true;
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
