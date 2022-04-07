package cpen221.mp1;

import cpen221.mp1.similarity.GroupingDocuments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

public class Task5SmokeTests {
    private static Document testDocument1, testDocument2, testDocument3, testDocument4;

    @BeforeAll
    public static void setupTests() throws MalformedURLException {
        testDocument1 = new Document("All same", "resources/task4_testFile_allSame.txt");
        testDocument2 = new Document("less mixed and small", "resources/task4_testFile_lessMixedAndSmall.txt");
        testDocument3 = new Document("unique and small", "resources/task4_testFile_uniqueAndSmall.txt");
        testDocument4 = new Document("mixed", "resources/task4_testFile_mixed.txt");
    }

    @Test
    public void allInOnePartition() {
        Set<Set<Document>> allDocs = new HashSet<>();
        Set<Document> docSet = new HashSet<>();
        docSet.add(testDocument1);
        docSet.add(testDocument2);
        docSet.add(testDocument3);

        for (Document d : docSet) {
            Set<Document> group = new HashSet<>();
            group.add(d);
            allDocs.add(group);
        }
        Assertions.assertEquals(allDocs, GroupingDocuments.groupBySimilarity(docSet, 3));
    }

    @Test
    public void allInOwnPartition() {
        Set<Set<Document>> allDocs = new HashSet<>();
        Set<Document> docSet = new HashSet<>();
        docSet.add(testDocument1);
        docSet.add(testDocument2);
        docSet.add(testDocument3);

        allDocs.add(docSet);

        Assertions.assertEquals(allDocs, GroupingDocuments.groupBySimilarity(docSet, 1));
    }

    @Test
    public void oneMerge() {
        Set<Set<Document>> allDocs = new HashSet<>();
        Set<Document> docSet = new HashSet<>();
        docSet.add(testDocument1);
        docSet.add(testDocument2);
        docSet.add(testDocument3);

        Set<Document> group1 = new HashSet<>();
        group1.add(testDocument2);
        group1.add(testDocument3);
        allDocs.add(group1);

        Set<Document> group2 = new HashSet<>();
        group2.add(testDocument1);
        allDocs.add(group2);

        Assertions.assertEquals(allDocs, GroupingDocuments.groupBySimilarity(docSet, 2));
    }

    @Test
    public void twoMerges() {
        Set<Set<Document>> allDocs = new HashSet<>();
        Set<Document> docSet = new HashSet<>();
        docSet.add(testDocument1);
        docSet.add(testDocument2);
        docSet.add(testDocument3);
        docSet.add(testDocument4);

        Set<Document> group1 = new HashSet<>();
        group1.add(testDocument2);
        group1.add(testDocument4);
        group1.add(testDocument3);
        allDocs.add(group1);

        Set<Document> group2 = new HashSet<>();
        group2.add(testDocument1);
        allDocs.add(group2);

        Assertions.assertEquals(allDocs, GroupingDocuments.groupBySimilarity(docSet, 2));
    }

    @Test
    public void SevenMerges_SameDocumentReferences() {
        Set<Set<Document>> allDocs = new HashSet<>();
        Set<Document> docSet = new HashSet<>();
        docSet.add(testDocument1);

        Document testDoc1 = testDocument1;
        docSet.add(testDoc1);

        Document testDoc2 = testDocument1;
        docSet.add(testDoc2);

        Document testDoc3 = testDocument1;
        docSet.add(testDoc3);

        Document testDoc4 = testDocument1;
        docSet.add(testDoc4);

        Document testDoc5 = testDocument1;
        docSet.add(testDoc5);

        Document testDoc6 = testDocument1;
        docSet.add(testDoc6);

        Document testDoc7 = testDocument1;
        docSet.add(testDoc7);

        Set<Document> group1 = new HashSet<>();
        group1.add(testDocument1);
        group1.add(testDoc1);
        group1.add(testDoc2);
        group1.add(testDoc3);
        group1.add(testDoc4);
        group1.add(testDoc5);
        group1.add(testDoc6);
        allDocs.add(group1);

        Set<Document> group2 = new HashSet<>();
        group2.add(testDoc7);
        allDocs.add(group2);

        Assertions.assertEquals(allDocs, GroupingDocuments.groupBySimilarity(docSet, 2));
    }
}
