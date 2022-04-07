package cpen221.mp1.similarity;

import cpen221.mp1.AnalyzeSentence;
import cpen221.mp1.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DocumentSimilarity {

    private final int WT_AVG_WORD_LENGTH = 5;
    private final int WT_UNIQUE_WORD_RATIO = 15;
    private final int WT_HAPAX_LEGOMANA_RATIO = 25;
    private final int WT_AVG_SENTENCE_LENGTH = 1;
    private final int WT_AVG_SENTENCE_CPLXTY = 4;
    private final int WT_JS_DIVERGENCE = 50;

    /**
     * Compute the Jensen-Shannon Divergence between the given documents
     *
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Jensen-Shannon Divergence between the given documents such that 0.0 <= divergence <= 1.0.
     */
    public double jsDivergence(Document doc1, Document doc2) {
        Set<String> uniqueWords = new HashSet<>();
        Set<String> doc1Words = new HashSet<>(wordsSetInDocument(doc1));
        Set<String> doc2Words = new HashSet<>(wordsSetInDocument(doc2));

        uniqueWords.addAll(doc1Words);
        uniqueWords.addAll(doc2Words);
        double numWordsInDoc1 = doc1.averageSentenceLength() * doc1.numSentences();
        double numWordsInDoc2 = doc2.averageSentenceLength() * doc2.numSentences();

        double jsDivergence = 0.0;
        double mean, probabilityOfWord_doc1, probabilityOfWord_doc2;

        for (String word : uniqueWords) {
            probabilityOfWord_doc1 = 0.0;
            probabilityOfWord_doc2 = 0.0;
            if (doc1Words.contains(word)) {
                probabilityOfWord_doc1 = 1.0 * numWordsInDocument(word, doc1) / numWordsInDoc1;
            }
            if (doc2Words.contains(word)) {
                probabilityOfWord_doc2 = 1.0 * numWordsInDocument(word, doc2) / numWordsInDoc2;
            }
            mean = (probabilityOfWord_doc1 + probabilityOfWord_doc2) / 2.0;

            if (probabilityOfWord_doc1 != 0) {
                jsDivergence +=
                        probabilityOfWord_doc1 *
                                (Math.log(probabilityOfWord_doc1 / mean) / Math.log(2));
            }
            if (probabilityOfWord_doc2 != 0) {
                jsDivergence +=
                        probabilityOfWord_doc2 *
                                (Math.log(probabilityOfWord_doc2 / mean) / Math.log(2));
            }
        }
        return jsDivergence / 2.0;
    }

    /**
     * Compute the Document Divergence between the given documents. A greater value indicates a greater dissimilarity between documents.
     *
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Document Divergence between the given documents such that the divergence >= 0.0.
     */
    public double documentDivergence(Document doc1, Document doc2) {
        double divergence = 0;
        final double[] weights = {WT_AVG_WORD_LENGTH, WT_UNIQUE_WORD_RATIO, WT_HAPAX_LEGOMANA_RATIO,
                WT_AVG_SENTENCE_LENGTH, WT_AVG_SENTENCE_CPLXTY};
        final double[] doc1_values =
                {doc1.averageWordLength(), doc1.uniqueWordRatio(), doc1.hapaxLegomanaRatio(),
                        doc1.averageSentenceLength(), doc1.averageSentenceComplexity()};
        final double[] doc2_values =
                {doc2.averageWordLength(), doc2.uniqueWordRatio(), doc2.hapaxLegomanaRatio(),
                        doc2.averageSentenceLength(), doc2.averageSentenceComplexity()};
        final double[] value_difference = new double[5];
        for (int i = 0; i < 5; i++) {
            value_difference[i] = Math.abs(doc1_values[i] - doc2_values[i]);
        }
        for (int i = 0; i < 5; i++) {
            divergence += weights[i] * value_difference[i];
        }
        divergence += WT_JS_DIVERGENCE * jsDivergence(doc1, doc2);

        return divergence;
    }

    /**
     * Computes the number of times a word appears in a Document.
     *
     * @param word     the word to check in the document.
     * @param document the Document to check the appearance of a word for
     * @return the number of appearances of the word in the Document, which is >= 0.0.
     */
    private int numWordsInDocument(String word, Document document) {
        int numWords = 0;
        for (int i = 1; i <= document.numSentences(); i++) {
            numWords += wordAppearanceInSentence(word, document.getSentence(i));
        }
        return numWords;
    }

    /**
     * Computes the number of times a word appears in a sentence.
     *
     * @param word     the word to check in the sentence
     * @param sentence the sentence to check the appearance of a word for
     * @return the number of appearance of the word in the sentence, which is >= 0.
     */
    private int wordAppearanceInSentence(String word, String sentence) {
        HashMap<String, Integer> words = new HashMap<>(AnalyzeSentence.wordsInSentence(sentence));
        return words.getOrDefault(word, 0);
    }

    /**
     * Obtains a set of words that are included in the document.
     *
     * @param document the Document where the words are found
     * @return a set containing the words that appear within the document
     */
    private Set<String> wordsSetInDocument(Document document) {
        Set<String> words = new HashSet<>();
        int numSentences = document.numSentences();
        for (int i = 1; i <= numSentences; i++) {
            words.addAll(AnalyzeSentence.wordsInSentence(document.getSentence(i)).keySet());
        }
        return words;
    }
}
