import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 This class reads files and store info to data structures
 lastly it will validate the arguments and run the UI class in the main method
 */
public class ProcessData {

    private static Set<String> bizId = new HashSet<>();

    /**  This method reads review and qa file and store the following:
     - store term as key and <id -> frequency> as value to invertedIndex data structure
     - store id as key and review/ qa object as key to idMap data structure
     - store id as key and asin as value to idAsinMap data structure
     * @param filePaths a list of file paths
     * @param type file type
     * @return a list of object that hold info
     */
    public static List<Object> readFile(List<String> filePaths, String type) {
        InvertedIndex invertedIndex = new InvertedIndex(); //<term -> <id -> freq>>
        IdMap idMap = new IdMap(); //<id -> reviews>
        BusinessIdMap businessIdMap = new BusinessIdMap(); //<businessId -> businessInfo>
        List<Object> output = new ArrayList<>();
        String line;
        String indicator;
        int docID = 0;
        Gson gson = new Gson();

        if (type.equals("reviews")) {
            indicator = filePaths.get(1);
        }
        else{ // business info
            indicator = filePaths.get(0);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(indicator)))) { // try with resource
            while ((line = br.readLine()) != null) {
                if ((!line.equals(""))) {
                    try { //skip bad line
                        if (type.equals("reviews")) {
                            Reviews rw = gson.fromJson(line, Reviews.class);
                            if(rw.getStars() > 4) {
                                rw.setDocID(docID);
                                UtilityMap.addToInvertedIndex(invertedIndex, rw);
                                UtilityMap.addToIdMap(idMap, docID, rw);
                                docID++;
                                System.out.println("review id: " + docID);
                            }

                        } else if (type.equals("business")) {
                            BusinessInfo biz = gson.fromJson(line, BusinessInfo.class);
                            String bizId = biz.getBusiness_id();
                            UtilityMap.addToBusinessIdMap(businessIdMap, bizId, biz);
                            System.out.println("biz id: " + bizId);


                        }
                    } catch (com.google.gson.JsonSyntaxException e) {
                        System.out.println("skip a bad line...");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("fail to read the file");
            e.printStackTrace();
        }

        if (type.equals("reviews")) {
            output.add(invertedIndex);
            output.add(idMap);
        }
        else{
            if(BusinessIdMap.size() != 0) {
                output.add(businessIdMap);
            }
        }
        return output;
    }


//    /**
//     * parse the user inputs
//     * @param args list of argument strings
//     * @return list of file paths
//     */
//    public static List<String> validateFileArgs(String[] args){
//        String reviewFile = null;
//        String qnaFile = null;
//        List<String> paths = new ArrayList<>();
//        for(int i = 0; i < args.length; i++){
//            if(args[i].equals("-reviews")){
//                reviewFile = args[i+1];
//            }
//            else if(args[i].equals("-qa")){
//                qnaFile = args[i+1];
//            }
//        }
//
//        System.out.println("review file: " + reviewFile);
//        System.out.println("qna file: " + qnaFile);
//
//        if(reviewFile == null || qnaFile == null){
//            System.out.println("Empty review file path or qna path");
//            return null;
//        }
//        paths.add(reviewFile);
//        paths.add(qnaFile);
//        return paths;
//    }


//    /**
//     * process User interface operations
//     * @param paths list of file paths
//     */
//    public static void run(List<String> paths){
//        if (paths != null && paths.size() == 2) {
//            UI ui = new UI(paths);
//            ui.processFiles();
//            ui.userInput();
//        }
//    }


//    public static void main(String[] args) {
//        List<String> paths = validateFileArgs(args);
//        run(paths);
//    }


}