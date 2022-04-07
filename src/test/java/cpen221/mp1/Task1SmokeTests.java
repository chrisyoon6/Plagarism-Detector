package cpen221.mp1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class Task1SmokeTests {

    private static Document testDocument1, testDocument2, testDocument3, testDocument4, testDocument5,
            exception, sameScore, sentence1, sentence2, sentence3, sentence4, sentence5, sentence6, sentence7;

    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new Document("The Ant and The Cricket", "resources/antcrick.txt");
        testDocument2 = new Document("The Ant and The Cricket",
                new URL("http://textfiles.com/stories/antcrick.txt"));
        testDocument3 = new Document("Empty File", "resources/emptyFile.txt");
        testDocument4 = new Document("file of phrases", "resources/phrasesTest.txt");
        testDocument5 = new Document("file of sentences", "resources/sentencesText.txt");
        exception = new Document("exceptionFile", "resources/exception.txt");
        sameScore = new Document("SameScoreFile", "resources/sameScore.txt");
        sentence1 = new Document("sentence1", "resources/sentence1.txt");
        sentence2 = new Document("sentence2", "resources/sentence2.txt");
        sentence3 = new Document("sentence3", "resources/sentence3.txt");
        sentence4 = new Document("sentence4", "resources/sentence4.txt");
        sentence5 = new Document("sentence5", "resources/sentence5.txt");
        sentence6 = new Document("sentence5", "resources/sentence6.txt");
        sentence7 = new Document("sentence5", "resources/sentence7.txt");
    }

    /* avg sentence length */
    @Test
    public void testAvgSentenceLengthOfEmpty() {
        Assertions.assertEquals(0, testDocument3.averageSentenceLength(), 0.005);
    }

    @Test
    public void testAvgSentenceLength1() {
        Assertions.assertEquals(10.027, testDocument1.averageSentenceLength(), 0.005);
    }

    @Test
    public void testAvgSentenceLength2() {
        double expected = 84.0 / 9;
        Assertions.assertEquals(expected, testDocument5.averageSentenceLength());
    }

    @Test
    public void testAvgSentenceLength3() {
        double expected = 56.0 / 3;
        Assertions.assertEquals(expected, sentence4.averageSentenceLength());
    }

    @Test
    public void testAvgSentenceLength4() {
        double expected = 372.0 / 37;
        Assertions.assertEquals(expected, testDocument2.averageSentenceLength());
    }

    /* avg sentence complexity */
    @Test
    public void testAvgSentenceComplexityOfEmpty() {
        Assertions.assertEquals(0, testDocument3.averageSentenceComplexity(), 0.005);
    }

    @Test
    public void testAvgSentenceComplexity1() {
        Assertions.assertEquals(1.702, testDocument2.averageSentenceComplexity(), 0.005);
    }

    @Test
    public void testAvgSentenceComplexity2() {
        double expected = 41.0 / 13;
        Assertions.assertEquals(expected, testDocument4.averageSentenceComplexity());
    }

    @Test
    public void testAvgSentenceComplexity3() {
        double expected = 4.0 / 4;
        Assertions.assertEquals(expected, exception.averageSentenceComplexity());
    }

    @Test
    public void testAvgSentenceComplexity4() {
        double expected = 5.0 / 3;
        Assertions.assertEquals(expected, sentence4.averageSentenceComplexity());
    }

    @Test
    public void testAvgSentenceComplexity5() {
        double expected = 63.0 / 37;
        Assertions.assertEquals(expected, testDocument2.averageSentenceComplexity());
    }

    /* docID */
    @Test
    public void testFileOfSentences1() {
        Assertions.assertEquals("file of phrases", testDocument4.getDocId());
    }

    @Test
    public void testFileOfSentences2() {
        Assertions.assertEquals("sentence3", sentence3.getDocId());
    }

    @Test
    public void testFileOfSentences3() {
        Assertions.assertEquals("The Ant and The Cricket", testDocument2.getDocId());
    }

    /* numSentences method */
    @Test
    public void testNumSentencesOfEmpty() {
        Assertions.assertEquals(0, testDocument3.numSentences());
    }

    @Test
    public void testNumSentences1() {
        Assertions.assertEquals(37, testDocument1.numSentences());
    }

    @Test
    public void testNumSentences2() {
        Assertions.assertEquals(9, testDocument5.numSentences());
    }

    @Test
    public void testSentenceCount3() {
        int expected = 6;
        Assertions.assertEquals(expected, sameScore.numSentences());
    }

    @Test
    public void testSentenceCount4() {
        int expected = 1;
        Assertions.assertEquals(expected, sentence2.numSentences());
    }

    @Test
    public void testSentenceCount5() {
        int expected = 37;
        Assertions.assertEquals(expected, testDocument2.numSentences());
    }

    /* getSentences */
    @Test
    public void testGetSentences1() {
        Assertions.assertEquals(
                "\"We can't do that,\" they said, \"We must store away food for the winter.",
                testDocument2.getSentence(5));
    }

    @Test
    public void testGetSentences2() {
        Assertions.assertEquals(
                "Mp 1 is very fun.", sameScore.getSentence(1));
    }

    @Test
    public void testGetSentences3() {
        Assertions.assertEquals(
                "I went to school today and had dinner at the penthouse.", exception.getSentence(4));
    }

    @Test
    public void testGetSentences4() {
        Assertions.assertEquals(
                "I'd rather sing!", testDocument2.getSentence(8));
    }

    @Test
    public void edgeCase_phrases_words() {
        Assertions.assertEquals(1, sentence6.hapaxLegomanaRatio());
        Assertions.assertEquals(1, sentence7.averageSentenceComplexity());
    }
}
