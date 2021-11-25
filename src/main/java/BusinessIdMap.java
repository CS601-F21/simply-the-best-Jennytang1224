import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BusinessIdMap {
    private static Map<String, Object> businessIdMap = new ConcurrentHashMap<>();

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
    public static int size(){
        int size = 0;
        for (Map.Entry<String, Object> entry: businessIdMap.entrySet()) {
            size++;
        }
        return size;
    }

}
