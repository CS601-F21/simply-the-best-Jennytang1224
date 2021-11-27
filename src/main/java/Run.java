import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.*;

public class Run {
    public static void main(String[] args) throws IOException {
        String log4jConfPath = "src/main/resource/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        IdMap idMapRW = new IdMap();
        InvertedIndex invertedIndexRW = new InvertedIndex();
        List<String> files = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        SentimentAnalysis.init();
        SentimentAnalysis.loadStopWords();


        files.add("data/yelp_academic_dataset_business_food.json");
        files.add("data/yelp_academic_dataset_review_food.json");

        List<Object> bizList = ProcessData.readFile(files, "business");
        BusinessIdMap idMapBiz = (BusinessIdMap) bizList.get(0);
        System.out.println("...Finishing reading business info file...");

        List<Object> rwList = ProcessData.readFile(files, "reviews");
        System.out.println("...Finishing reading review file...");


        for(Object o : rwList){
            if (o instanceof InvertedIndex){
                invertedIndexRW = (InvertedIndex) o;
            }
            else{
                idMapRW = (IdMap) o;
            }
        }

        Search search = new Search();
        String term = "crepe";
        int k = 3;
        Map<String, Integer> businessWithPosCnt = search.MakeBusinessWithPosRWsMap(idMapRW, invertedIndexRW, term);
        search.displayTopKBusiness(term, businessWithPosCnt, idMapBiz, k);

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in seconds: " + timeElapsed / 1000);
    }


}
