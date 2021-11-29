package YelpSearch;

import java.util.*;

/**
 * YelpSearch.InvertedIndex class a data structure for better searching text.
 * its a hashmap maps term -> another frequency hashmap, which maps id -> term frequency in the object with this id
 */
public class InvertedIndex {

    private final Map<String, HashMap<Integer, Integer>> invertedIndexMap = new HashMap<>();

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
        int docID = 0;
        List<String> processed = new ArrayList<>();
        docID = ((Reviews) obj).getDocID();
        processed = processText(((Reviews) obj).getReviewText());

        for (String term : processed) {
            if (!invertedIndexMap.containsKey(term)) {
                Map<Integer, Integer> freq = new HashMap<>();
                freq.put(docID, 1);
                invertedIndexMap.put(term, (HashMap<Integer, Integer>) freq);

            } else if (invertedIndexMap.containsKey(term) && !containsDocId(docID, term)) {
                invertedIndexMap.get(term).put(docID, 1);

            } else { // term is in map && contains docID
                int count = invertedIndexMap.get(term).get(docID);
                invertedIndexMap.get(term).put(docID, count + 1);
            }
        }
    }

    /**
     * return the value in the map associated with the term
     * @param  term a given term
     * @return value in the invertedIndex associated with the term
     */
    public HashMap<Integer, Integer> get(String term) {
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
        for (Map.Entry<String, HashMap<Integer, Integer>> entry: invertedIndexMap.entrySet()) {
            size++;
        }
        return size;
    }

}
