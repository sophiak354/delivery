package com.solvd.delivery.fileutil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {
    public static void countWords(List<String> words) throws IOException {
        File article = new File("src/main/resources/article.txt");
        File out = new File("src/main/resources/out.txt");

        String text = FileUtils.readFileToString(article, StandardCharsets.UTF_8).toLowerCase();

        String[] tokens = StringUtils.split(text, " \n\r\t.,!?;:\"()[]");

        String result = words.stream()
                .map(word -> {
                    long count = Arrays.stream(tokens)
                            .filter(token ->
                                    token.equals(word.toLowerCase())
                                            || token.equals(word.toLowerCase() + "'s"))
                            .count();
                    return word + " - " + count;
                })
                .collect(Collectors.joining("\n", "", "\n"));
        FileUtils.writeStringToFile(out, result, StandardCharsets.UTF_8, true);
    }
}
