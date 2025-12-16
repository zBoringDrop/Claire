package it.unict.davidemilazzo.claire.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineSimilarity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CodeManagement {

    private static final int DEFAULT_LINE_COUNT_IN_BLOCK = 50;
    private static final int DEFAULT_BLOCK_OVERLAP = 20;

    public static List<String> splitCode(String code, int lineInBlock, int overlap) {
        List<String> lines = IOUtils.readLines(new StringReader(code));
        if (lines.size() <= lineInBlock) {
            return List.of(code);
        }

        List<String> snippetsToAnalyze = new ArrayList<>();
        StringBuilder codeSnippet = new StringBuilder();

        for (int i=0, j=0; i<lines.size(); i++, j++) {
            codeSnippet.append(lines.get(i)).append("\n");

            if (j >= lineInBlock-1) {
                snippetsToAnalyze.add(codeSnippet.toString());
                codeSnippet = new StringBuilder();
                j = 0;
                i -= overlap;
            }
        }

        if (!codeSnippet.isEmpty()) {
            snippetsToAnalyze.add(codeSnippet.toString());
        }

        return snippetsToAnalyze;
    }

    public static List<String> splitCode(String code) {
        return splitCode(code, DEFAULT_LINE_COUNT_IN_BLOCK, DEFAULT_BLOCK_OVERLAP);
    }

    private static Map<CharSequence, Integer> getTermsFrequence(String text) {
        Map<CharSequence, Integer> termsFrequence = new HashMap<>();

        String[] terms = text.toLowerCase().trim()
                .replaceAll("([a-z])([A-Z])", "$1 $2")
                .split("[^A-Za-z0-9]+");

        for (String word : terms) {
            if (word.isBlank()) continue;
            Integer oldValue = termsFrequence.getOrDefault(word, 0);
            termsFrequence.put(word, oldValue+1);
        }

        return termsFrequence;
    }

    public static double getStringSimilarityPerc(String first, String second) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        return cosineSimilarity.cosineSimilarity(getTermsFrequence(first), getTermsFrequence(second));
    }
}
