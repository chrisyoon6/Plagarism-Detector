package cpen221.mp1;

import cpen221.mp1.exceptions.NoSuitableSentenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class Task3SmokeTests {
    private static Document testDocument1, testDocument2, exception, SameScore, mostPositiveFirst, mostNegativeFirst;

    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new Document("The Ant and The Cricket", "resources/antcrick.txt");
        testDocument2 = new Document("The Ant and The Cricket",
            new URL("http://textfiles.com/stories/antcrick.txt"));
        exception = new Document("exceptionFile", "resources/exception.txt");
        SameScore = new Document("SameScoreFile", "resources/sameScore.txt");
        //mostPositiveFirst = new Document("Most positive first", "resources/mostPositiveFirst.txt");
        //mostNegativeFirst = new Document("Most negative first", "resources/mostNegativeFirst.txt");
    }

    @Test
    public void testMostPositiveSentence() {
        try {
            Assertions.assertEquals("Yes!", testDocument1.getMostPositiveSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMostPositiveSentence2() {
        try {
            Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",
                    testDocument1.getMostNegativeSentence());
            Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",
                    testDocument1.getMostNegativeSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPositiveSameScore() {
        try {
            Assertions.assertEquals("Yes I would love to come!",
                SameScore.getMostPositiveSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMostNegativeSentence() {
        try {
            Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",
                testDocument1.getMostNegativeSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMostNegativeSentence2() {
        try {
            Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",
                testDocument2.getMostNegativeSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noSuitableSentenceExceptionPositive() {
        boolean thrown = false;
        try {
            exception.getMostPositiveSentence();
        } catch (NoSuitableSentenceException e) {
            thrown = true;
        }
        Assertions.assertTrue(thrown);
    }

    @Test
    public void noSuitableSentenceExceptionNegative() {
        boolean thrown = false;
        try {
            exception.getMostNegativeSentence();
        } catch (NoSuitableSentenceException e) {
            thrown = true;
        }
        Assertions.assertTrue(thrown);
    }

    @Test
    public void testMostPositiveSentenceCache() {
        try {
            Assertions.assertEquals("Yes!", testDocument1.getMostPositiveSentence());
            Assertions.assertEquals("Yes!", testDocument1.getMostPositiveSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMostNegativeSentenceCache() {
        try {
            Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",
                testDocument1.getMostNegativeSentence());
            Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",
                testDocument1.getMostNegativeSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMostNegativeSentenceFirst() {
        try {
            Assertions.assertEquals("Then the snow fell and she could find nothing at all to eat.",
                mostPositiveFirst.getMostNegativeSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMostPositiveSentenceFirst() {
        try {
            Assertions.assertEquals("Yes I would love to come!",
                mostNegativeFirst.getMostPositiveSentence());
        } catch (NoSuitableSentenceException e) {
            e.printStackTrace();
        }
    }
}
