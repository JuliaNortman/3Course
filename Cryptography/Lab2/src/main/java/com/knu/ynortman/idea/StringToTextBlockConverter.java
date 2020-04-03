package com.knu.ynortman.idea;

import java.util.ArrayList;
import java.util.List;

public class StringToTextBlockConverter {
    public List<TextBlock> convert(String stringToConvert) {
        List<TextBlock> listOfBitArrays = new ArrayList<>();
        for(int i = 0;i < (stringToConvert.length() / 8) + 1;i++) {
            int[] values = new int[8];

            for (int j = 0;j < 8;j++) {
                int index = i * 8 + j;
                if (index < stringToConvert.length()) {
                    values[j] = (int)stringToConvert.charAt(index);
                } else {
                    values[j] = 0;
                }
            }

            listOfBitArrays.add(new TextBlock(values));
        }

        return listOfBitArrays;
    }
}