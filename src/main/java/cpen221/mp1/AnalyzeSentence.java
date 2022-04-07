package cpen221.mp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * For the purposes of this AnalyzeSentence class, we define a valid phrase as a
 * non-empty (empty = empty string or whitespace only) part of a sentence this is
 * separated from another phrase by commas, colons, and semi-colons.
 * By extension, we can then define a valid word as non-empty token that is not completely
 * comprised of punctuation. If a token begins or ends with punctuation, the word can be
 * obtained by removing all starting and trailing punctuation. Note a single # can be
 * supported as a stand-alone word.
 */

public class AnalyzeSentence {
    private static final Character[] invalid_trailing_punc_draft =
        {'!', '#', '"', '$', '%', '&', '(', ')', '*', '+', '-',
            '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '\'', '^', '_', '`', '{', '|',
            '}', '~', ',', '.', '\n'};
    private static final Set<Character> invalid_trailing_punc =
        new HashSet<>(Arrays.asList(invalid_trailing_punc_draft));
    private static final Character[] invalid_opening_punc_draft =
        {'!', '"', '$', '%', '&', '(', ')', '*', '+', '-',
            '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '\'', '^', '_', '`', '{', '|',
            '}', '~', ' ', ',', '.'};
    private static final Set<Character> invalid_opening_punc =
        new HashSet<>(Arrays.asList(invalid_opening_punc_draft));

    /**
     * Computes the number of valid phrases in a sentence.
     *
     * @param sentence sentence to check, and is not null
     * @return the number of phrases in {@code sentence}
     */
    public static int numPhrasesInSentence(String sentence) {
        int startOfPhrase = 0;
        int endOfPhrase = 0;
        int phraseCount = 0;
        StringBuilder sentence_sb = new StringBuilder(sentence);
        int sentenceLength = sentence_sb.length();
        System.out.println(sentence_sb);
        for (int i = 0; i < sentence_sb.length(); i++) {
            char currentCharInSentence = sentence_sb.charAt(i);
            if (currentCharInSentence == ',' || currentCharInSentence == ':' ||
                currentCharInSentence == ';' || i == sentenceLength - 1) {

                endOfPhrase = i;

                String potentialPhrase;
                if (!(currentCharInSentence == ',' || currentCharInSentence == ':' ||
                    currentCharInSentence == ';') && i == sentenceLength - 1) {
                    potentialPhrase = sentence.substring(startOfPhrase);
                } else {
                    potentialPhrase = sentence.substring(startOfPhrase, endOfPhrase);
                }
                if (!potentialPhrase.isEmpty() && !onlyWhiteSpace(potentialPhrase)) {
                    phraseCount++;
                }

                startOfPhrase = endOfPhrase + 1;
            }
        }
        return phraseCount;
    }

    /**
     * Obtains all valid words in a sentence and the number of times they appear.
     *
     * @param sentence sentence to check, and is not null
     * @return a map of key-value pairs such that the key represents a word in
     * {@code sentence} and the value represents the number of times that word
     * appears
     */
    public static HashMap<String, Integer> wordsInSentence(String sentence) {
        char[] workingSentence = sentence.toLowerCase().toCharArray();
        StringBuilder potentialWord = new StringBuilder();
        HashMap<String, Integer> words = new HashMap<>();

        boolean needStartingPoint = true;
        for (int i = 0; i < workingSentence.length; i++) {
            if (invalid_opening_punc.contains(workingSentence[i]) && needStartingPoint) {
                continue;
            }
            if (!(invalid_opening_punc.contains(workingSentence[i])) && needStartingPoint) {
                needStartingPoint = false;
            }

            if (workingSentence[i] == ' ' ||
                i == workingSentence.length - 1 && !needStartingPoint) {
                if (i == workingSentence.length - 1) {
                    potentialWord.append(workingSentence[i]);
                }

                String word = deleteTrailingPunc(potentialWord);

                if (words.containsKey(word)) {
                    int numAppearance = words.get(word);
                    words.put(word, numAppearance + 1);
                } else {
                    words.put(word, 1);
                }
                potentialWord.setLength(0);
                needStartingPoint = true;
                continue;
            }
            potentialWord.append(workingSentence[i]);
        }
        return words;
    }

    /**
     * Computes the total number of valid words in a sentence.
     *
     * @param sentence sentence to check, and is not null
     * @return the number of words in {@code sentence}
     */
    public static int numWordsInSentence(String sentence) {
        HashMap<String, Integer> words = new HashMap<>(wordsInSentence(sentence));
        int numberOfWords = 0;
        for (Integer appearances : words.values()) {
            numberOfWords += appearances;
        }
        return numberOfWords;
    }

    /**
     * Computes the total length of all words in a sentence.
     *
     * @param sentence sentence to check, and is not null
     * @return the total length of all words in {@code sentence}
     */
    public static int wordLengthInSentence(String sentence) {
        HashMap<String, Integer> words = new HashMap<>(wordsInSentence(sentence));
        ArrayList<String> wordsInSentence = new ArrayList<>(words.keySet());
        ArrayList<Integer> wordAppearances = new ArrayList<>(words.values());
        int size = words.size();
        int wordLengthInSentence = 0;

        for (int i = 0; i < size; i++) {
            wordLengthInSentence += wordsInSentence.get(i).length() * wordAppearances.get(i);
        }
        return wordLengthInSentence;
    }

    /**
     * Helper method for checking whether a string only contains whitespace.
     *
     * @param str string to be checked, and is not null nor empty
     * @return true if {@code str} is only composed of whitespace
     */
    private static boolean onlyWhiteSpace(String str) {
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method that removes all invalid trailing punctuation in a potential word.
     *
     * @param potentialWord word to be modified if invalid trailing punctuations exist
     * @return {@code potentialWord} with all invalid trailing punctuation removed
     */
    private static String deleteTrailingPunc(StringBuilder potentialWord) {
        for (int i = potentialWord.length() - 1; i >= 0; i--) {
            if (i == 0 && potentialWord.charAt(i) == '#') {
                break;
            }
            if (!(invalid_trailing_punc.contains(potentialWord.charAt(i)))) {
                break;
            }
            potentialWord.deleteCharAt(i);
        }
        return potentialWord.toString();
    }
}
