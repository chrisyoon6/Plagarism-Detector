package cpen221.mp1.sentiments;

import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import cpen221.mp1.exceptions.NoSuitableSentenceException;

import java.io.IOException;

public class SentimentAnalysis {

    /**
     * Uses the sentiment analyzer to find the most positive sentence in a document.
     *
     * @param doc the document to be analyzed
     * @return the most positive sentence in the document, if there are 2 with the highest score,
     * return the one that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence in the documents that express a
     *                                     negative sentiment
     */
    public static String getMostPositiveSentence(cpen221.mp1.Document doc)
            throws NoSuitableSentenceException {
        double highestScore = getSentimentAnalysis(doc.getSentence(1));
        int highestSentenceNumber = 1;
        double score;

        for (int i = 2; i < doc.numSentences(); i++) {
            String sentence = doc.getSentence(i);
            score = getSentimentAnalysis(sentence);

            if (score >= highestScore) {
                highestScore = score;
                highestSentenceNumber = i;
            }
        }

        if (highestScore < 0.3) {
            throw new NoSuitableSentenceException();
        }
        return doc.getSentence(highestSentenceNumber);
    }

    /**
     * Uses the sentiment analyzer to find the most negative sentence in a document.
     *
     * @param doc the document to be analyzed
     * @return the most negative sentence in the document, if there are 2 with the highest score
     * return the one that appears later in the document
     * @throws NoSuitableSentenceException if there is no sentence in the documents that express a
     *                                     negative sentiment
     **/
    public static String getMostNegativeSentence(cpen221.mp1.Document doc)
            throws NoSuitableSentenceException {
        double lowestScore = getSentimentAnalysis(doc.getSentence(1));
        int lowestSentenceNumber = 1;
        double score;

        for (int i = 2; i < doc.numSentences(); i++) {
            String sentence = doc.getSentence(i);
            score = getSentimentAnalysis(sentence);

            if (score <= lowestScore) {
                lowestScore = score;
                lowestSentenceNumber = i;
            }
        }

        if (lowestScore > -0.3) {
            throw new NoSuitableSentenceException();
        }
        return doc.getSentence(lowestSentenceNumber);
    }

    private static double getSentimentAnalysis(String sentence) {
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc =
                    Document.newBuilder().setContent(sentence).setType(Type.PLAIN_TEXT).build();
            AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
            Sentiment sentiment = response.getDocumentSentiment();

            return sentiment.getScore();

        } catch (IOException ioe) {
            System.out.println(ioe);
            throw new RuntimeException("Unable to communicate with Sentiment Analyzer!");
        }
    }
}