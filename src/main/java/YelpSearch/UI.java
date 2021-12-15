package YelpSearch;

import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.List;


/**
 * Class to run the program
 */
public class UI {
    private final List<String> paths;
    private InvertedIndex invertedIndexRW = new InvertedIndex(); //<term -> <id -> freq>>
    private IdMap idMapRW = new IdMap(); //<id -> reviews>
    private BusinessIdMap idMapBiz = new BusinessIdMap();


    public UI(List<String> paths){
        this.paths = paths;
    }


    /**
     * read both review and business files
     */
    public void processFiles(){
        SentimentAnalysis.init();
        try {
            SentimentAnalysis.loadStopWords();
        }
        catch(IOException e){
            System.out.println("fail to load stop words");
        }
        long startTime = System.currentTimeMillis();

        List<Object> bizList = ProcessData.readFile(paths, "business");
        System.out.println("...Finishing reading business info file...");
        List<Object> rwList = ProcessData.readFile(paths, "reviews");
        System.out.println("...Finishing reading review file...");

        idMapBiz = (BusinessIdMap) bizList.get(0);
        for(Object o : rwList){
            if (o instanceof InvertedIndex){
                invertedIndexRW = (InvertedIndex) o;
            }
            else{
                idMapRW = (IdMap) o;
            }
        }

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in seconds: " + timeElapsed / 1000);
    }

    public BusinessIdMap getIdMapBiz(){
        return idMapBiz;
    }

    public InvertedIndex getInvertedIndexRW(){
        return invertedIndexRW;
    }

    public IdMap getIdMapRW(){
        return idMapRW;
    }

}
