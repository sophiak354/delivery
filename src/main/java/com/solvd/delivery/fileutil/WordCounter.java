package com.solvd.delivery.fileutil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WordCounter {
    public static void countWords(List<String> words) throws IOException {
        File article = new File("src/main/resources/article.txt");
        File out = new File("src/main/resources/out.txt");

        String text = FileUtils.readFileToString(article, StandardCharsets.UTF_8).toLowerCase();

        String[] tokens = StringUtils.split(text, " \n\r\t.,!?;:\"()[]");

        StringBuilder result = new StringBuilder();

        for (String word : words) {
            int count = 0;

            for (String token : tokens) {
                if (token.equals(word.toLowerCase()) || token.equals(word.toLowerCase() + "'s")) {
                    count++;
                }
            }
            result.append(word).append(" - ").append(count).append("\n");
        }
        FileUtils.writeStringToFile(out, result.toString(), StandardCharsets.UTF_8, true);
    }
}
