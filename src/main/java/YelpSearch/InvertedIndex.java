package YelpSearch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * YelpSearch.InvertedIndex class a data structure for better searching text.
 * its a hashmap maps term -> another frequency hashmap, which maps id -> term frequency in the object with this id
 */
public class InvertedIndex {

    private final Map<String, ConcurrentHashMap<Integer, Integer>> invertedIndexMap = new ConcurrentHashMap<>();

    /**
     * strip strip non alphanumeric in a word
     * @param str a string
     * @return a new string without punctuations
     */
    public StringBuilder stripPunctuations(String str){
        StringBuilder newStr = new StringBuilder();
        for (Character character : str.toCharArray()) {
            if(Character.isLetterOrDigit(character))
                newStr.append(character);
        }
        return newStr;
    }


    /**
     * use stripPunctuations method to process text and return a list of words without non-alphanumerics
     * @param txt a text with strings
     * @return a list of words without non-alphanumeric characters
     */
    public List<String> processText(String txt){
        List<String> wordList = new ArrayList<>();
        for (String s: txt.split(" ")){
            String newStr = stripPunctuations(s).toString();
            wordList.add(newStr.toLowerCase());
        }
        return wordList;
    }


    /**
     * check if the term corresponding to the the hashmap contains docID
     * @param docID document ID
     * @param term a given term
     * @return boolean if a docID is in the hashmap
     */
    public boolean containsDocId(int docID, String term) {
        return this.invertedIndexMap.get(term).containsKey(docID);
    }


    /**
     * add new reviews/qa object to the hashmap <term -> (docID->frequency)>
     * @param obj the object we want to add to the invertedIndex
     */
    public void add(Object obj) {
//        if (((YelpSearch.Reviews) obj).getStars() < 4.0){ // only use the review has stars 4 or 5
//            return;
//        }

        int docID = 0;
        List<String> processed = new ArrayList<>();
        docID = ((Reviews) obj).getDocID();
        processed = processText(((Reviews) obj).getReviewText());
//        processed = YelpSearch.SentimentAnalysis.removeStopWords(processText(((YelpSearch.Reviews) obj).getReviewText()));

        for (String term : processed) {
//            // if term is
//            int counter = 0;
//            String type;
//            // find sentences with the term
//            String sentence = findRelatedSentences(((YelpSearch.Reviews) obj).getReviewText(), term);
//            if(sentence == null){
//                continue;
//            }
//
//            if(sentenceMap.containsKey(sentence)){ //check if sentence has been analyzed already
//                type = sentenceMap.get(sentence);
//            }
//            else{ // if haven't been analyzed
//                //sentiment analysis on the term, avg the score on sentences within same review -> if positive, counter ++
//                type = YelpSearch.SentimentAnalysis.analyze(sentence);
//                sentenceMap.put(sentence, type);
//            }
//
//            System.out.println(term + " " + type + ": \n" + sentence);

//            if(type.equalsIgnoreCase("very positive") || type.equalsIgnoreCase("positive")) {

            if (!invertedIndexMap.containsKey(term)) {
                Map<Integer, Integer> freq = new ConcurrentHashMap<>();
                freq.put(docID, 1);
                invertedIndexMap.put(term, (ConcurrentHashMap<Integer, Integer>) freq);

            } else if (invertedIndexMap.containsKey(term) && !containsDocId(docID, term)) {
                invertedIndexMap.get(term).put(docID, 1);

            } else { // term is in map && contains docID
                int count = invertedIndexMap.get(term).get(docID);
                invertedIndexMap.get(term).put(docID, count + 1);
            }

        }
    }


//
//    /**
//     * in a frequency hashmap, sort the docID(keys) by frequency(values)
//     * @param freqMap this is the value field in invertedMap <id -> freq>
//     * @return a sorted freqMap
//     */
//    public Map<Integer, Integer> sortHashMap(ConcurrentHashMap<Integer, Integer> freqMap){
//        List<Map.Entry<Integer, Integer>> lst = new LinkedList<>(freqMap.entrySet());
//        Collections.sort(lst, new Comparator<>() {
//            // compare reviewFreq object by frequency value
//            @Override
//            public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
//                return e2.getValue().compareTo(e1.getValue());
//            }
//        });
//        Map<Integer, Integer> sortedMap = new ConcurrentHashMap<>();
//        for (Map.Entry<Integer, Integer> entry : lst)
//        {
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }
//        return sortedMap;
//
//    }
//
//
//    /**
//     * sort all values in YelpSearch.InvertedIndex map by the frequency
//     */
//    public void sortAll(){
//        for (Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry: invertedIndexMap.entrySet()) {
//            Map<Integer, Integer> sorted = sortHashMap(entry.getValue());
//            entry.setValue((ConcurrentHashMap<Integer, Integer>) sorted);
//        }
//    }


    /**
     * return the value in the map associated with the term
     * @param  term a given term
     * @return value in the invertedIndex associated with the term
     */
    public ConcurrentHashMap<Integer, Integer> get(String term) {
        return invertedIndexMap.get(term);
    }


    /**
     * Returns a set of keys in invertedIndex
     * @return set of keys
     */
    public Set<String> keySet() {
        return invertedIndexMap.keySet();
    }


    /**
     * Returns size of the invertedIndex
     * @return size
     */
    public int size(){
        int size = 0;
        for (Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry: invertedIndexMap.entrySet()) {
            size++;
        }
        return size;
    }

}
