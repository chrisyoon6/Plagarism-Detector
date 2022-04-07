package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import cpen221.mp1.sentiments.SentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.*;

/**
 * For the purpose of this Document class, we define a valid sentence as a sequence of
 * characters that 1) is terminated by (and includes) the characters ! ? . or the end
 * of the file, 2) excludes whitespace on either end, and 3) is not empty.
 */

public class Document {
    private String docId;
    private int sentenceCount;
    private int wordCount;
    private int phraseCount;
    private int totalWordLength;
    private HashMap<String, Integer> words;
    private ArrayList<String> sentences;
    private HashMap<Document, String> mostPosSentenceCache;
    private HashMap<Document, String> mostNegSentenceCache;
    private String fileText;

    /**
     * Create a new document using an URL and initialize member variables.
     *
     * @param docId  the document identifier
     * @param docURL the URL with the contents of the document
     */
    public Document(String docId, URL docURL) {
        initializeVars();
        this.docId = docId;

        try {
            StringBuilder lines = new StringBuilder();
            Scanner urlScanner = new Scanner(docURL.openStream());

            while (urlScanner.hasNext()) {
                lines.append(urlScanner.nextLine()).append(' ');
            }
            if (lines.length() == 0) {
                lines.append(' ');
            } else {
                lines.deleteCharAt(lines.length() - 1);
            }
            fileText = lines.toString();

        } catch (IOException ioe) {
            System.out.println("Problem reading from URL!");
        }
        iterateThroughText();
    }

    /**
     * Create a new document using a text file and initialize member variables.
     *
     * @param docId    the document identifier
     * @param fileName the name of the file with the contents of the document
     */
    public Document(String docId, String fileName) {
        initializeVars();
        this.docId = docId;

        try {
            StringBuilder lines = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            for (String fileLine = reader.readLine(); fileLine != null; fileLine = reader.readLine()) {
                lines.append(fileLine).append(' ');
            }
            if (lines.length() == 0) {
                lines.append(' ');
            } else {
                lines.deleteCharAt(lines.length() - 1);
            }
            fileText = lines.toString();
            reader.close();

        } catch (IOException ioe) {
            System.out.println("Problem reading file!");
        }
        iterateThroughText();
    }

    /**
     * Obtain the identifier for this document.
     *
     * @return the identifier for this document
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Obtain the number of sentences in this document.
     *
     * @return the number of sentences in this document
     */
    public int numSentences() {
        return sentenceCount;
    }

    /**
     * Obtain a specific sentence from this document.
     * Note that all sentences are numbered starting from 1.
     *
     * @param sentence_number the position of the sentence to retrieve,
     *                        {@code 1 <= sentence_number <= this.numSentences()}
     * @return the sentence indexed by {@code sentence_number}
     */
    public String getSentence(int sentence_number) {
        return sentences.get(sentence_number - 1);
    }

    /**
     * Computes the average number of words per sentence in this document.
     *
     * @return the average sentence length for this document
     */
    public double averageSentenceLength() {
        if (sentenceCount == 0) {
            return 0.0;
        }
        return 1.0 * wordCount / sentenceCount;
    }

    /**
     * Computes the average number of phrases per sentence in this document.
     *
     * @return the average sentence complexity for this document
     */
    public double averageSentenceComplexity() {
        if (sentenceCount == 0) {
            return 0.0;
        }
        return 1.0 * phraseCount / sentenceCount;
    }

    /**
     * Computes the average word length in this document.
     *
     * @return the average word length for this document
     */
    public double averageWordLength() {
        if (wordCount == 0) {
            return 0.0;
        }
        return 1.0 * totalWordLength / wordCount;
    }

    /**
     * Computes the number of unique words to the total number of words in this document.
     *
     * @return the unique word ratio for this document such that the ratio is >= 0
     */
    public double uniqueWordRatio() {
        if (wordCount == 0) {
            return 0.0;
        }
        return 1.0 * words.size() / wordCount;
    }

    /**
     * Computes the number of unique words occurring only once to the total number
     * of words in this document.
     *
     * @return the Hapax Legomana ratio for this document such that the ratio is >= 0
     */
    public double hapaxLegomanaRatio() {
        int hapaxWordCount = 0;

        for (Integer numberOfAppearances : words.values()) {
            if (numberOfAppearances == 1) {
                hapaxWordCount++;
            }
        }

        if (wordCount == 0) {
            return 0.0;
        }
        return 1.0 * hapaxWordCount / wordCount;
    }

    /**
     * Obtain the sentence with the most positive sentiment in the document.
     *
     * @modifies the mostPositiveSentenceCache to contain a sentence if it
     * does not already contain it
     * @return the sentence with the most positive sentiment in the
     * document; when multiple sentences share the same sentiment value
     * returns the sentence that appears later in the document.
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a positive sentiment
     */
    public String getMostPositiveSentence() throws NoSuitableSentenceException {
        if (mostPosSentenceCache.containsKey(this)) {
            return mostPosSentenceCache.get(this);

        } else {
            String sentence = SentimentAnalysis.getMostPositiveSentence(this);
            mostPosSentenceCache.put(this, sentence);
            return sentence;
        }
    }

    /**
     * Obtain the sentence with the most negative sentiment in the document
     *
     * @modifies mostNegativeCache to contain the sentence if it does not
     * already contain it
     * @return the sentence with the most negative sentiment in the document;
     * when multiple sentences share the same sentiment value, returns the
     * sentence that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence that
     *                                     expresses a negative sentiment
     */
    public String getMostNegativeSentence() throws NoSuitableSentenceException {
        if (mostNegSentenceCache.containsKey(this)) {
            return mostNegSentenceCache.get(this);

        } else {
            String sentence = SentimentAnalysis.getMostNegativeSentence(this);
            mostNegSentenceCache.put(this, sentence);
            return sentence;
        }
    }

    /**
     * Initializes all instance variables for this document.
     */
    private void initializeVars() {
        sentenceCount = 0;
        wordCount = 0;
        phraseCount = 0;
        totalWordLength = 0;
        words = new HashMap<>();
        sentences = new ArrayList<>();
        mostPosSentenceCache = new HashMap<>();
        mostNegSentenceCache = new HashMap<>();
    }

    /**
     * Iterates through each sentence of this document and updates all
     * instance variables accordingly.
     */
    private void iterateThroughText() {
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(fileText);
        int start = iterator.first();

        StringBuilder sentence_sb = new StringBuilder();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            sentence_sb.append(fileText.substring(start, end));

            if (sentence_sb.charAt(sentence_sb.length() - 1) == ' ') {
                sentence_sb.deleteCharAt(sentence_sb.length() - 1);
            }

            String sentence = sentence_sb.toString();
            if (!sentence.isEmpty()) {
                sentences.add(sentence_sb.toString());
                sentenceCount++;
            }

            phraseCount += AnalyzeSentence.numPhrasesInSentence(sentence);
            wordCount += AnalyzeSentence.numWordsInSentence(sentence);
            addToWords(AnalyzeSentence.wordsInSentence(sentence));
            totalWordLength += AnalyzeSentence.wordLengthInSentence(sentence);

            sentence_sb.setLength(0);
        }
    }

    /**
     * Takes new words and their appearance count, and updates instance
     * variables accordingly.
     *
     * @param _words map of words and their appearance count to be used to
     *               update instance variables
     */
    private void addToWords(HashMap<String, Integer> _words) {
        ArrayList<String> wordsInSentence = new ArrayList<>(_words.keySet());
        ArrayList<Integer> appearance = new ArrayList<>(_words.values());

        for (int i = 0; i < _words.size(); i++) {
            String currentWord = wordsInSentence.get(i);
            int numAppearance = appearance.get(i);

            if (words.containsKey(currentWord)) {
                int originalValue = words.get(currentWord);
                words.put(currentWord, originalValue + numAppearance);
            } else {
                words.put(currentWord, numAppearance);
            }
        }
    }
}

