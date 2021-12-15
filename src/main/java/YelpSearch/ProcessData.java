package YelpSearch;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 This class reads files and store info to data structures
 */
public class ProcessData {

    private static Set<String> bizId = new HashSet<>();

    /**  This method reads review and business file and store them data structure
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
                                //System.out.println("review id: " + docID);
                            }

                        } else if (type.equals("business")) {
                            BusinessInfo biz = gson.fromJson(line, BusinessInfo.class);
                            String bizId = biz.getBusiness_id();
                            UtilityMap.addToBusinessIdMap(businessIdMap, bizId, biz);
                            //System.out.println("biz id: " + bizId);
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
                output.add(businessIdMap);
        }
        return output;
    }


}