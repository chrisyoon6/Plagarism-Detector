package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.util.*;

public class GroupingDocuments {
    /**
     * Group documents by similarity.
     *
     * @param allDocuments   the set of all documents to be considered,
     *                       and is not null
     * @param numberOfGroups the number of document groups to be generated,
     *                       where 1 < {@code numberOfGroups} < size of {@code allDocuments}
     * @return groups of documents, where each group (set) contains similar
     * documents following this rule: if D_i is in P_x, and P_x contains at
     * least one other document, then P_x contains some other D_j such that
     * the divergence between D_i and D_j is smaller than (or at most equal
     * to) the divergence between D_i and any document that is not in P_x.
     */
    public static Set<Set<Document>> groupBySimilarity(Set<Document> allDocuments, int numberOfGroups) {
        Set<Set<Document>> finalGroups = new HashSet<>();
        Set<Set<Document>> workingGroups = new HashSet<>();
        Map<ArrayList<Document>, Double> divScores = computeDivScores(allDocuments);

        Set<Document> oneGroup = new HashSet<>();
        if (numberOfGroups == 1) {
            for (Document d : allDocuments) {
                oneGroup.add(d);
            }
            workingGroups.add(oneGroup);
            return workingGroups;
        }

        for (Document d : allDocuments) {
            Set<Document> eachGroup = new HashSet<>();
            eachGroup.add(d);
            workingGroups.add(eachGroup);
        }
        if (numberOfGroups == allDocuments.size()) {
            return workingGroups;
        }

        int groupCount = allDocuments.size();
        double minDivScore;
        ArrayList<Document> docPair = new ArrayList<>();
        docPair.add(null);
        docPair.add(null);

        while (groupCount > numberOfGroups) {
            minDivScore = Double.MAX_VALUE;
            for (Map.Entry<ArrayList<Document>, Double> docCombo : divScores.entrySet()) {
                if (docCombo.getValue() < minDivScore) {
                    minDivScore = docCombo.getValue();
                    docPair.set(0, docCombo.getKey().get(0));
                    docPair.set(1, docCombo.getKey().get(1));
                }
            }
            divScores.put(docPair, Double.MAX_VALUE);

            for (Set<Document> referenceGroup : workingGroups) {
                if (referenceGroup.contains(docPair.get(0))) {
                    for (Set<Document> mergingGroup : workingGroups) {
                        if (mergingGroup.contains(docPair.get(1))) {
                            if (!(referenceGroup.equals(mergingGroup))) {
                                referenceGroup.addAll(mergingGroup);
                                mergingGroup.clear();
                                groupCount--;
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }

        for (Set<Document> groups : workingGroups) {
            if (!(groups.isEmpty())) {
                finalGroups.add(groups);
            }
        }
        return finalGroups;
    }

    /**
     * Computes the divergence score for all pairs of documents within a given set.
     *
     * @param allDocuments the set of documents to be considered, and is not null
     * @return a map of key-value pairs such that the key contains a list of two documents being
     * compared, and the value being their divergence score
     */
    private static Map<ArrayList<Document>, Double> computeDivScores(Set<Document> allDocuments) {
        ArrayList<Document> allDocs = new ArrayList<>();
        Map<ArrayList<Document>, Double> divScores = new HashMap<>();

        allDocs.addAll(allDocuments);

        for (int i = 0; i < allDocs.size(); i++) {
            for (int j = i + 1; j < allDocs.size(); j++) {
                ArrayList<Document> docPair = new ArrayList<>();
                docPair.add(allDocs.get(i));
                docPair.add(allDocs.get(j));
                divScores.put(docPair, new DocumentSimilarity().documentDivergence(docPair.get(0), docPair.get(1)));
            }
        }
        return divScores;
    }
}
