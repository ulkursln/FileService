package com.springboot.rest.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.springboot.rest.dto.model.LongestLineDTO;

public class LineGenerator {
    private static final int MAX_STRING_LENGTH = 40;
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";

    public static List<LongestLineDTO> generateLine(int numStrings) {
        List<LongestLineDTO> result = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < numStrings; i++) {
            StringBuilder sb = new StringBuilder();
            int length = random.nextInt(3, MAX_STRING_LENGTH) + 1; // Random length between 1 and MAX_STRING_LENGTH (inclusive)

            for (int j = 0; j < length; j++) {
                int index = random.nextInt(ALLOWED_CHARACTERS.length());
                char c = ALLOWED_CHARACTERS.charAt(index);
                sb.append(c);
            }

            result.add(new LongestLineDTO(sb.toString(),"file"+i,sb.length()));
        }

        return result;
    }
}
