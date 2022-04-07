package cpen221.mp1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class Task2SmokeTests {

    private static Document testDocument1, testDocument2, testDocument3, testDocument4, testDocument5,
            exception, sameScore, sentence1, sentence2, sentence3, sentence4, sentence5;

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
    }

    /* avg word length */
    @Test
    public void testAvgEmptyLengthOfEmpty() {
        Assertions.assertEquals(0.0, testDocument3.averageWordLength(), 0.005);
    }

    @Test
    public void testAvgWordLength1() {
        Assertions.assertEquals(4.08, testDocument1.averageWordLength(), 0.005);
    }

    @Test
    public void testAvgWordLength2() {
        Assertions.assertEquals(1.0 * 92 / 28, sameScore.averageWordLength(), 0.005);
    }

    @Test
    public void testAvgWordLength3() {
        Assertions.assertEquals(1.0 * 105 / 13, sentence1.averageWordLength(), 0.005);
    }

    @Test
    public void testAvgWordLength4() {
        Assertions.assertEquals(1516.0 / 372, testDocument2.averageWordLength(), 0.005);
    }

    @Test
    public void testAvgWordLength5() {
        Assertions.assertEquals(303.0 / 49, sentence3.averageWordLength(), 0.005);
    }

    /* unique word ratio */
    @Test
    public void testUniqueWordRatioOfEmpty() {
        Assertions.assertEquals(0.0, testDocument3.uniqueWordRatio(), 0.005);
    }

    @Test
    public void testUniqueWordRatio1() {
        Assertions.assertEquals(0.524, testDocument2.uniqueWordRatio(), 0.005);
    }

    @Test
    public void testUniqueWordRatio2() {
        Assertions.assertEquals(22.0 / 24, exception.uniqueWordRatio(), 0.005);
    }

    @Test
    public void testUniqueWordRatio3() {
        Assertions.assertEquals(51.0 / 63, sentence5.uniqueWordRatio(), 0.005);
    }

    @Test
    public void testUniqueWordRatio4() {
        Assertions.assertEquals(195.0 / 372, testDocument2.uniqueWordRatio(), 0.005);
    }


    /* hapax word ratio */
    @Test
    public void testHapaxLegomanaRatioOfEmpty() {
        Assertions.assertEquals(0.0, testDocument3.hapaxLegomanaRatio(), 0.005);
    }

    @Test
    public void testHapaxLegomanaRatio1() {
        Assertions.assertEquals(0.355, testDocument1.hapaxLegomanaRatio(), 0.005);
    }

    @Test
    public void testHapaxLegomanaRatio2() {
        double expected = 0.4642857142857143;
        Assertions.assertEquals(expected, sameScore.hapaxLegomanaRatio());
    }

    @Test
    public void testHapaxLegomanaRatio3() {
        double expected = 1;
        Assertions.assertEquals(expected, sentence2.hapaxLegomanaRatio());
    }

    @Test
    public void testHapaxLegomanaRatio4() {
        double expected = 42.0 / 63;
        Assertions.assertEquals(expected, sentence5.hapaxLegomanaRatio());
    }

    @Test
    public void testHapaxLegomanaRatio5() {
        double expected = 133.0 / 372;
        Assertions.assertEquals(expected, testDocument2.hapaxLegomanaRatio());
    }
}
