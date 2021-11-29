package YelpSearch;

import java.util.HashMap;
import java.util.Map;

public class BusinessIdMap {
    private final Map<String, Object> businessIdMap =  new HashMap<>(); //(businessID -> business obj)

    public void put(String businessId, Object obj){
        businessIdMap.put(businessId, obj);
    }

    public BusinessInfo getValue(String key){
        return (BusinessInfo)businessIdMap.get(key);
    }

    /**
     * Returns size of the idMap
     * @return size
     */
    public int size(){
        int size = 0;
        for (Map.Entry<String, Object> entry: businessIdMap.entrySet()) {
            size++;
        }
        return size;
    }

}
