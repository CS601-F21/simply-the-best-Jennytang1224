package YelpSearch;

import HTTP.HTTPServerConstants;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class Search {
    private static final Pattern END_OF_SENTENCE = Pattern.compile(".$");//("\\.\\s+");
    private static final String trailingRegex = "\\s+$";
    private static final String leadingRegex =  "^\\s+";
    private Map<String, String> sentenceMap;
    private Map<String, Integer> businessWithPosCnt; //(business id -> pos count)
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


    //get review ids where review contains the term from invertedindex
    public Set<Integer> getReviewIds(String term){
        if(invertedIndexRW.get(term.toLowerCase()) == null){
            return null;
        }

        Set<Integer> docIds = invertedIndexRW.get(term.toLowerCase()).keySet();
        return docIds;
    }


    // use reviewIds to get reviews, keep only 5 stars review,
    // select sentence only contains the term in the review, (filter out sentence more than 15 words long) and use sentiment analysis
    // if positive add to the business
    public Map<String, Integer> MakeBusinessWithPosRWsMap(String term){
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
            String sentence = findRelatedSentences(review.getReviewText(), term);
            if(sentence == null){
                continue;
            }
            sentence = sentence.replaceAll(leadingRegex, "").replaceAll(trailingRegex,"");
            List<String> lst = SentimentAnalysis.removeStopWords(sentence.split(" "));

            String newSentence = String.join(" ", lst);
            if(newSentence.split(" ").length > 20){
                count_long++;
                continue;
            }
            //check if sentence has been analyzed already
            if(sentenceMap.containsKey(newSentence)){
                type = sentenceMap.get(newSentence);
            }
            else{  // if haven't been analyzed
                //sentiment analysis on the term, avg the score on sentences within same review -> if positive, counter ++
                type = SentimentAnalysis.analyze(newSentence.toLowerCase());
                sentenceMap.put(newSentence, type);
            }
            //System.out.println(term + " ->  " + type + ": \n" + sentence);
            if(type.equalsIgnoreCase("very positive") || type.equalsIgnoreCase("positive")) {
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

    // use businessWithPosCnt map
    public String displayTopKBusiness(String term, int k){
        int counter = 0;
        StringBuilder sb = new StringBuilder();

        businessWithPosCnt = MakeBusinessWithPosRWsMap(term);
        if (businessWithPosCnt != null) {
            //System.out.println(" *** Top " + k + " restaurant(s) with best " + term + " : ***");
           // sb.append("<br><h1>Top ").append(k).append(" restaurant(s) with best '").append(term).append("': </h1></br>");
            sb.append(HTTPServerConstants.RESULT_MSG_PART1).append(k).append(HTTPServerConstants.RESULT_MSG_PART2).append(term).append(HTTPServerConstants.RESULT_MSG_PART3);

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
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    //find first sentence within a review with a given term
    public String findRelatedSentences(String reviewText, String term){
        final String lcword = term.toLowerCase();
        for (String sentence : reviewText.split("[,?.@!;-]+", -2)) {
            if (sentence.toLowerCase().contains(lcword)) {
                return sentence;
            }
        }
        return null;
    }

}
