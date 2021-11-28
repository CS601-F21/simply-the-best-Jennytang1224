package YelpSearch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class Search {
    private static final Pattern END_OF_SENTENCE = Pattern.compile(".$");//("\\.\\s+");
    private Map<String, String> sentenceMap;
    private Map<String, Integer> businessWithPosCnt;
    private InvertedIndex invertedIndexRW;
    private IdMap idMapReviews;
    private BusinessIdMap idMapBiz;



    public Search(InvertedIndex invertedIndexRW, IdMap idMapReviews, BusinessIdMap idMapBiz){
        sentenceMap = new ConcurrentHashMap<>(); // sentence -> type
        businessWithPosCnt = new ConcurrentHashMap<>(); // businessID -> positiveFreq
        this.invertedIndexRW = invertedIndexRW;
        this.idMapReviews = idMapReviews;
        this.idMapBiz = idMapBiz;
    }

    /**
     * Given a one-word term, display a list of all reviews that contain the exact term.
     * results is sorted and the reviews with most frequency will show on the top
     * @param term a given term
     */
    //get review ids where review contains the term
    public Set<Integer> getReviewIds(String term){
        if(invertedIndexRW.get(term) == null){
            return null;
        }

        Set<Integer> docIds = invertedIndexRW.get(term).keySet();
        return docIds;
    }


    public Map<String, Integer> MakeBusinessWithPosRWsMap(String term){
        int counter = 0;
        int count_long = 0;
        String type;
        Set<Integer> reviewIds = getReviewIds(term);
        if(reviewIds == null){
            System.out.println("0 result found! Please try another term.");
            return null;
        }
        System.out.println("Number of reviews related to " + term + " : " + reviewIds.size());
        for(int id: reviewIds) {
            Reviews review = idMapReviews.getValue(id); // review related to the term
            // only look into the review has 5 stars
            if(review.getStars() < 5){
                continue;
            }
            //extract the sentence contains the key
            counter++;
            //System.out.println("----> count num of 5-star reviews with the term: " + counter);
            String sentence = findRelatedSentences(review.getReviewText(), term);
//            String sentence = review.getReviewText().toLowerCase();
            if(sentence == null){
                continue;
            }

            if(sentence.split(" ").length > 15){
//                System.out.println(sentence);

                count_long++;
//                String newSentence = "";
//                for (int i = 0; i < 10; i++) {
//                    newSentence = newSentence + " " + sentence.split(" ")[i];
//                }
//                sentence = newSentence;
                continue;
            }
            //check if sentence has been analyzed already
            if(sentenceMap.containsKey(sentence)){
                type = sentenceMap.get(sentence);
            }
            // if haven't been analyzed
            else{
                //sentiment analysis on the term, avg the score on sentences within same review -> if positive, counter ++
                type = SentimentAnalysis.analyze(sentence.toLowerCase());
                sentenceMap.put(sentence, type);
            }

            //System.out.println(term + " ->  " + type + ": \n" + sentence);

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
        System.out.println("count long reviews:" + count_long);
        return businessWithPosCnt;
    }


    public String displayTopKBusiness(String term, int k){
        int counter = 0;
        StringBuilder sb = new StringBuilder();

        businessWithPosCnt = MakeBusinessWithPosRWsMap(term);
        if (businessWithPosCnt != null) {
            System.out.println(" *** Top " + k + " restaurant(s) with best " + term + " : ***");
            sb.append("<br>*** Top ").append(k).append(" restaurant(s) with best ").append(term).append(" : *** </br>");
            Map<String, Integer> sorted = sortHashMap(businessWithPosCnt);
            for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
                if (counter >= k) {
                    break;
                }
                String businessId = entry.getKey();
                BusinessInfo bizInfo = idMapBiz.getValue(businessId);

                sb.append(bizInfo.toString());
                sb.append("<br> Number of positive reviews on ").append(term).append(": ").append(entry.getValue()).append("</br>");
                sb.append("<br> </br>");
                counter++;
            }
        }
        else{
            sb.append("<br>Sorry there's no review related to " + term + "</br>");
            return sb.toString();
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }


    /**
     * in a frequency hashmap, sort the docID(keys) by frequency(values)
     * @param freqMap this is the value field in invertedMap <id -> freq>
     * @return a sorted freqMap
     */
    public Map<String, Integer> sortHashMap(Map<String, Integer> freqMap){
        List<Map.Entry<String, Integer>> lst = new LinkedList<>(freqMap.entrySet());
        Collections.sort(lst, new Comparator<>() {

            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : lst) {
            //System.out.println("order: " + entry.getValue());
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    //find first sentence within a review with a given term
    public String findRelatedSentences(String reviewText, String term){
//        String sentence = END_OF_SENTENCE.splitAsStream(term)
//                .filter(s -> s.toLowerCase().contains(term.toLowerCase()))
//                .findAny()
//                .orElse(null); // return first match, if no match, return whole thing
//        return sentence;
        final String lcword = term.toLowerCase();
//        for (String sentence : END_OF_SENTENCE.split(reviewText)) {
        for (String sentence : reviewText.split("[,?.@!;-]+", -2)) {
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
