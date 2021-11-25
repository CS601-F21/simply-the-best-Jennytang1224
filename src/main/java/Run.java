import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Run {
    private static BusinessIdMap idMapBiz;
    private static IdMap idMapRW;
    private static InvertedIndex invertedIndexRW;
    private static BusinessIdMap businessIdMap;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        SentimentAnalysis.init();
        SentimentAnalysis.loadStopWords();

        List<String> files = new ArrayList<>();

        files.add("data/yelp_academic_dataset_business_food.json");
        List<Object> bizList = ProcessData.readFile(files, "business");
        System.out.println("finishing reading business info file");
        idMapBiz = (BusinessIdMap) bizList.get(0);


        files.add("data/yelp_academic_dataset_review_food.json");
        List<Object> rwList = ProcessData.readFile(files, "reviews");
        System.out.println("finishing reading review file");


        for(Object o : rwList){
            if (o instanceof InvertedIndex){
                invertedIndexRW = (InvertedIndex) o;
            }
            else{
                idMapRW = (IdMap) o;
            }
        }

        Search search = new Search();

        String term = "hot";
        int k = 3;
        Set<Integer> reviewIds = search.getReviewIds(term);
        Map<String, Integer>  businessWithPosCnt = search.MakeBusinessWithPosRWsMap(idMapRW, reviewIds, term);
        search.displayTopKBusiness(businessWithPosCnt, businessIdMap, k);

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in seconds: " + timeElapsed / 1000);
    }


}
