package com.funs.gifticonservice.global.util;


import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Random;

@Getter
@Component
public class SerialNumber {
        int serialNumberSize = 20;

        final char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();

        int possibleCharacterCount = possibleCharacters.length;

        String[] arr = new String[serialNumberSize];
        Random random = new Random();

        int currentIndex = 0;
        int i = 0;

        public String generateSerialNumber() {
            return generateSerialNumber(serialNumberSize, possibleCharacters, possibleCharacterCount, arr, random, currentIndex);
        }

    private static String generateSerialNumber(int serialNumberSize, char[] possibleCharacters, int possibleCharacterCount, String[] arr, Random random, int currentIndex) {
        int i;
        while (currentIndex < serialNumberSize) {
            StringBuilder sb = new StringBuilder(serialNumberSize);

            for (i = 0; i < serialNumberSize; i++) {
                sb.append(possibleCharacters[random.nextInt(possibleCharacterCount)]);
            }

            arr[currentIndex] = sb.toString();
            currentIndex++;
        }


        return arr[0];
    }

}
