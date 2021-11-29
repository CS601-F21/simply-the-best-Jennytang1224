package YelpSearch;

import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.List;


/**
 * YelpSearch.UI class is a data structure to prompt for user inout and manage all user operations.
 */
public class UI {
    private final List<String> paths;
    private InvertedIndex invertedIndexRW = new InvertedIndex(); //<term -> <id -> freq>>
    private IdMap idMapRW = new IdMap(); //<id -> reviews>
    private BusinessIdMap idMapBiz = new BusinessIdMap();


    /**
     * constructor for YelpSearch.UI class
     * @param paths a list of filepath strings
     */
    public UI(List<String> paths){
        this.paths = paths;
    }


    /**
     * read both review and qa files
     */
    public void processFiles(){
        String log4jConfPath = "src/main/resource/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
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
