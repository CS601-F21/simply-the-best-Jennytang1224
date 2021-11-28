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
        //System.out.println("finished reading the files and creating inverted index for both YelpSearch.Reviews and QAs.");
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

//
//    /**
//     * Given the ASIN number of a specific product, display a list of all reviews for that product
//     * and display a list of all questions and answers about that product.
//     * @param asin given asin
//     */
//    public String find(String asin){
//        StringBuffer sb = new StringBuffer();
//        Set<Integer> idListRW = asinMapRW.getIdsByAsin(asin);
//        Set<Integer> idListQA = asinMapQA.getIdsByAsin(asin);
//        int counter = 0;
//        int num = idListRW.size() + idListQA.size();
//        if(num == 0){
//            sb.append("<br>" + num).append(" result found! Please try another ASIN. </br>");
//        }
//        else{
//            sb.append("<br>" + num).append(" result(s) found: </br>");
//        }
//        if(idListRW.size() > 0) {
//            for (int id : idListRW) {
//                counter++;
//                System.out.println("Review: ");
//                sb.append("Review: ");
//                sb.append(idMapRW.displayText(id));
//            }
//        }
//        if(idListQA.size() > 0){
//            for (int id: idListQA) {
//                counter++;
//                System.out.println("QnA: ");
//                sb.append("QnA: ");
//                sb.append(idMapQA.displayText(id));
//            }
//        }
//        System.out.println(counter + " result(s) found!");
//        return sb.toString();
//
//    }
//
//
//    /**
//     * given a list of doc ids display the content
//     * @param idList a list of ids
//     * @param type review or qa
//     */
//    public String displayContent(Set<Integer> idList, String type){
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
//        if (type.equals("reviews")) {
//            for (int id : idList) {
//                sb.append(idMapRW.displayText(id));
//            }
//        }
//        else if (type.equals("qas")){
//            for (int id : idList) {
//                sb.append(idMapQA.displayText(id));
//            }
//        }
//        return sb.toString();
//    }
//
//
//    /**
//     * Given a one-word term, display a list of all reviews that contain the exact term.
//     * results is sorted and the reviews with most frequency will show on the top
//     * @param term a given term
//     */
//    public String reviewSearch(String term){
//        invertedIndexRW.sortAll();
//        if(invertedIndexRW.get(term) == null){
//            System.out.println("No results found");
//            return "0 result found! Please try another term.";
//        }
//
//        Set<Integer> docIds = invertedIndexRW.get(term).keySet();
//        return displayContent(docIds, "reviews");
//    }
//
//
//    /**
//     * Given a one-word term, list of all question/answer documents that have the exact term
//     * results is sorted and the qa with most frequency will show on the top
//     * @param term a given term
//     */
//    public void qaSearch(String term){
//        invertedIndexQA.sortAll();
//        if(invertedIndexQA.get(term) == null){
//            System.out.println("No results found");
//        }
//        else {
//            Set<Integer> docIds = invertedIndexQA.get(term).keySet();
//            displayContent(docIds, "qas");
//        }
//    }
//
//
//    /**
//     * Given a one-word term, display a list of all reviews where any word in the review contains a partial match
//     * to the term.
//     * @param partialTerm a given partial term
//     */
//    public void reviewPartialSearch(String partialTerm){
//        Set<Integer> docIds = new HashSet<>();
//        for(String term: invertedIndexRW.keySet()){
//            if(term.contains(partialTerm)){
//                Set<Integer> idSet = invertedIndexRW.get(term).keySet();
//                docIds.addAll(idSet);
//            }
//        }
//        displayContent(docIds, "reviews");
//    }
//
//
//    /**
//     * Given a one-word term, display a list of question/answer documents where any word in the question
//     * or answer contains a partial match to the term.
//     * @param partialTerm a given partial term
//     */
//    public void qaPartialSearch(String partialTerm){
//        Set<Integer> docIds = new HashSet<>();
//        for(String term: invertedIndexQA.keySet()){
//            if(term.contains(partialTerm)){
//                Set<Integer> idSet = invertedIndexQA.get(term).keySet();
//                docIds.addAll(idSet);
//            }
//        }
//        displayContent(docIds, "qas");
//    }
//
//
//    /**
//     * system prompt for user input with required logics
//     */
//    public void userInput(){
//        int flag = 0;
//        while (flag == 0) {
//            Scanner sc = new Scanner(System.in);
//            System.out.print(">>>");
//            String input = sc.nextLine();
//            String[] lst = input.split(" ");
//            if(lst.length == 2) {
//                if (lst[0].equalsIgnoreCase("find")) {
//                    find(lst[1].toLowerCase());
//                } else if (lst[0].equalsIgnoreCase("reviewsearch")) {
//                    reviewSearch(lst[1].toLowerCase());
//                } else if (lst[0].equalsIgnoreCase("qasearch")) {
//                    qaSearch(lst[1].toLowerCase());
//                } else if (lst[0].equalsIgnoreCase("reviewpartialsearch")) {
//                    reviewPartialSearch(lst[1].toLowerCase());
//                } else if (lst[0].equalsIgnoreCase("qapartialsearch")) {
//                    qaPartialSearch(lst[1].toLowerCase());
//                } else {
//                    System.out.println("Invalid input!");
//                    flag = 1;
//                }
//            }
//
//            else if (lst.length == 1 && lst[0].equalsIgnoreCase("exit")) {
//                System.out.println("Bye now!");
//                flag = 1;
//            }
//            else{
//                System.out.println("Invalid input!");
//                flag = 1;
//            }
//        }
//    }
}
