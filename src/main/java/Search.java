import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class Search {
    private static final Pattern END_OF_SENTENCE = Pattern.compile("\\.\\s+");
    private InvertedIndex invertedIndexRW = new InvertedIndex(); //<term -> <id -> freq>>
    private Set<Integer> positiveReviewIds;
    private Map<String, String> sentenceMap= new ConcurrentHashMap<>(); // sentence -> type
    private Map<String, Integer> businessWithPosCnt = new ConcurrentHashMap<>(); // businessID -> positiveFreq


    /**
     * Given a one-word term, display a list of all reviews that contain the exact term.
     * results is sorted and the reviews with most frequency will show on the top
     * @param term a given term
     */
    //get review ids where review contains the term
    public Set<Integer> getReviewIds(String term){
        //invertedIndexRW.sortAll();

        if(invertedIndexRW.get(term) == null){
            System.out.println("No results found");
            System.out.println("0 result found! Please try another term.");
            return null;
        }

        Set<Integer> docIds = invertedIndexRW.get(term).keySet();
        return docIds;
    }


    public Map<String, Integer> MakeBusinessWithPosRWsMap(IdMap idMapReviews, Set<Integer> reviewIds, String term){
        int counter = 0;
        String type;
        System.out.println("num of reviews related to term: " + reviewIds);
        for(int id: reviewIds) {
            Reviews review = idMapReviews.getValue(id); // review related to the term
            // only look into the review has >= 4 stars
            if(review.getStars() < 4){
                continue;
            }
            //extract the sentence contains the key
            String sentence = findRelatedSentences(review.getReviewText(), term);
            if(sentence == null){
                continue;
            }
            //check if sentence has been analyzed already
            if(sentenceMap.containsKey(sentence)){
                type = sentenceMap.get(sentence);
            }
            // if haven't been analyzed
            else{
                //sentiment analysis on the term, avg the score on sentences within same review -> if positive, counter ++
                type = SentimentAnalysis.analyze(sentence);
                sentenceMap.put(sentence, type);
            }

            System.out.println(term + " " + type + ": \n" + sentence);
            if(type.equalsIgnoreCase("very positive") || type.equalsIgnoreCase("positive")) {
                //positiveReviewIds.add(id);
                String businessId = review.getBusiness_id();
                if(!businessWithPosCnt.containsKey(businessId)) {
                    businessWithPosCnt.put(businessId, 1);
                }
                else{
                    businessWithPosCnt.put(businessId, businessWithPosCnt.get(businessId) + 1);
                }
            }
        }
        return businessWithPosCnt;
    }


    public void displayTopKBusiness(Map<String, Integer> businessWithPosCnt, BusinessIdMap businessIdMap, int k){
        int counter = 0;

        for (Map.Entry<String, Integer> entry : businessWithPosCnt.entrySet()) {
            if(counter > k){
                break;
            }
            String businessId = entry.getKey();
            BusinessInfo bizInfo = businessIdMap.getValue(businessId);
            bizInfo.toString();
            counter++;
        }
    }


    /**
     * in a frequency hashmap, sort the docID(keys) by frequency(values)
     * @param freqMap this is the value field in invertedMap <id -> freq>
     * @return a sorted freqMap
     */
    public Map<String, Integer> sortHashMap(Map<String, Integer> freqMap){
        List<Map.Entry<String, Integer>> lst = new LinkedList<>(freqMap.entrySet());
        Collections.sort(lst, new Comparator<>() {
            // compare reviewFreq object by frequency value
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        Map<String, Integer> sortedMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, Integer> entry : lst)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

//
//    /**
//     * sort all values in InvertedIndex map by the frequency
//     */
//    public void sortAll(){
//        for (Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry: invertedIndexMap.entrySet()) {
//            Map<Integer, Integer> sorted = sortHashMap(entry.getValue());
//            entry.setValue((ConcurrentHashMap<Integer, Integer>) sorted);
//        }
//    }




    //find first sentence within a review with a given term
    public String findRelatedSentences(String reviewText, String term){
//        String sentence = END_OF_SENTENCE.splitAsStream(term)
//                .filter(s -> s.toLowerCase().contains(term.toLowerCase()))
//                .findAny()
//                .orElse(null); // return first match, if no match, return whole thing
//        return sentence;
        final String lcword = term.toLowerCase();
        for (String sentence : END_OF_SENTENCE.split(reviewText)) {
            if (sentence.toLowerCase().contains(lcword)) {
                return sentence;
            }
        }
        return null;
    }




//
//
//    /**
//     * given a list of doc ids display the content
//     * @param idList a list of ids
//     */
//    public String displayContent(Set<Integer> idList){
//        StringBuffer sb = new StringBuffer();
//        if(idList.size() == 0){
//            System.out.println(idList.size() + " result(s) found:");
//            sb.append("<br>" + idList.size()).append(" result found! Please try another term. </br>");
//        }
//        else{
//            System.out.println(idList.size() + " result(s) found:");
//            sb.append("<br>" + idList.size()).append(" result(s) found: </br>");
//        }
//
//        for (int id : idList) {
//            sb.append(idMapRW.displayText(id));
//        }
//
//        return sb.toString();
//    }

}
