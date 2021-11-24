import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BusinessIdMap {
    private final Map<String, Object> BusinessIdMap = new ConcurrentHashMap<>();

    public void put(String businessId, Object obj){
        BusinessIdMap.put(businessId, obj);
    }


    public BusinessInfo getValue(String key){
        return (BusinessInfo)BusinessIdMap.get(key);
    }

}
