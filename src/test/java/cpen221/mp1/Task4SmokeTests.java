package cpen221.mp1;

import cpen221.mp1.similarity.DocumentSimilarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

public class Task4SmokeTests {
    private static Document testDocument1, testDocument2, testDocument3, testDocument4,
            testDocument5, testDocument6, testDocument7;

    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new Document("All same", "resources/task4_testFile_allSame.txt");
        testDocument2 =
                new Document("less mixed and small", "resources/task4_testFile_lessMixedAndSmall.txt");
        testDocument3 =
                new Document("unique and small", "resources/task4_testFile_uniqueAndSmall.txt");
        testDocument4 = new Document("mixed", "resources/task4_testFile_mixed.txt");
        testDocument5 = new Document("empty", "resources/task4_testFile_empty.txt");
        testDocument6 = new Document("All same", "resources/task4_testFile_allSame.txt");
        testDocument7 =
                new Document("unique and small 2", "resources/task4_testFile_uniqueAndSmall2.txt");
    }

    @Test
    public void JSDmixedWords_mixedWords_noSame() {
        DocumentSimilarity documentSimilarity = new DocumentSimilarity();
        double[] p_doc1 =
                {1.0 / 4, 0, 0, 1.0 / 4, 0, 1.0 / 4, 1.0 / 4};
        double[] p_doc2 =
                {0, 1.0 / 3, 1.0 / 3, 0, 1.0 / 3, 0, 0};
        double[] mean = new double[12];
        for (int i = 0; i < p_doc1.length; i++) {
            mean[i] = (p_doc1[i] + p_doc2[i]) / 2;
        }
        double expected = 0;
        for (int i = 0; i < p_doc1.length; i++) {
            if (p_doc1[i] != 0) {
                expected += p_doc1[i] * (Math.log(p_doc1[i] / mean[i]) / Math.log(2));
            }
            if (p_doc2[i] != 0) {
                expected += p_doc2[i] * (Math.log(p_doc2[i] / mean[i]) / Math.log(2));
            }
        }
        expected /= 2.0;
        Assertions.assertEquals(expected,
                documentSimilarity.jsDivergence(testDocument3, testDocument7));
    }

    @Test
    public void JSDallSame_allSame() {
        DocumentSimilarity documentSimilarity = new DocumentSimilarity();
        double expected = 0;
        Assertions.assertEquals(0, documentSimilarity.jsDivergence(testDocument1, testDocument6));
    }

    @Test
    public void JSDmixedWords_mixedWords_someSame() {
        DocumentSimilarity documentSimilarity = new DocumentSimilarity();
        double[] p_doc1 =
                {1.0 / 14, 1.0 / 14, 2.0 / 14, 3.0 / 14, 1.0 / 14, 2.0 / 14, 1.0 / 14, 1.0 / 14,
                        1.0 / 14, 1.0 / 14, 0, 0};
        double[] p_doc2 =
                {1.0 / 7, 1.0 / 7, 0.0, 1.0 / 7, 1.0 / 7, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0 / 7, 1.0 / 7};
        double[] mean = new double[12];
        for (int i = 0; i < 12; i++) {
            mean[i] = (p_doc1[i] + p_doc2[i]) / 2;
        }
        double expected = 0;
        for (int i = 0; i < p_doc1.length; i++) {
            if (p_doc1[i] != 0) {
                expected += p_doc1[i] * (Math.log(p_doc1[i] / mean[i]) / Math.log(2));
            }
            if (p_doc2[i] != 0) {
                expected += p_doc2[i] * (Math.log(p_doc2[i] / mean[i]) / Math.log(2));
            }
        }
        expected /= 2.0;
        Assertions.assertEquals(expected,
                documentSimilarity.jsDivergence(testDocument4, testDocument2));
    }

    @Test
    public void JSD_empty_mixed() {
        DocumentSimilarity documentSimilarity = new DocumentSimilarity();
        double[] p_doc1 =
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] p_doc2 =
                {1.0 / 7, 1.0 / 7, 0.0, 1.0 / 7, 1.0 / 7, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0 / 7, 1.0 / 7};
        double[] mean = new double[12];
        for (int i = 0; i < 12; i++) {
            mean[i] = (p_doc1[i] + p_doc2[i]) / 2;
        }
        double expected = 0;
        for (int i = 0; i < p_doc1.length; i++) {
            if (p_doc1[i] != 0) {
                expected += p_doc1[i] * (Math.log(p_doc1[i] / mean[i]) / Math.log(2));
            }
            if (p_doc2[i] != 0) {
                expected += p_doc2[i] * (Math.log(p_doc2[i] / mean[i]) / Math.log(2));
            }
        }
        expected /= 2.0;
        Assertions.assertEquals(expected,
                Math.round(documentSimilarity.jsDivergence(testDocument5, testDocument2) * 100000) /
                        100000.0);
    }

    @Test
    public void DocumentDivergence_allSame() {
        DocumentSimilarity documentSimilarity = new DocumentSimilarity();
        double expected = 0.0;
        Assertions.assertEquals(expected,
                documentSimilarity.documentDivergence(testDocument1, testDocument6));
    }

    @Test
    public void DocumentDivergence_noneSame() {
        DocumentSimilarity documentSimilarity = new DocumentSimilarity();
        double expected = 9.75 + 50 * documentSimilarity.jsDivergence(testDocument3, testDocument7);
        Assertions.assertEquals(expected,
                documentSimilarity.documentDivergence(testDocument3, testDocument7));
    }

    @Test
    public void DocumentDivergence_someSame() {
        DocumentSimilarity documentSimilarity = new DocumentSimilarity();
        double expected =
                12.571428 + 50 * documentSimilarity.jsDivergence(testDocument4, testDocument2);
        expected = Math.round(expected * 100000) / 100000.0;
        double actual =
                Math.round(
                        documentSimilarity.documentDivergence(testDocument4, testDocument2) * 100000) /
                        100000.0;
        Assertions.assertEquals(expected, actual);
    }
}