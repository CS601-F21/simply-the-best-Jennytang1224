package YelpSearch;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * idMap data structure is a hashmap that maps Id to Review objects
 */
public class IdMap{
    private final Map<Integer, Object> idMap = new HashMap<>(); //(id -> review obj)

    /**
     * add items to the idMap
     * @param docID key
     * @param obj value
     */
    public void put(int docID, Object obj){
        idMap.put(docID, obj);
    }

    public Set<Integer> getKeys(){
        return idMap.keySet();
    }

    public Object[] getValues(){
        return idMap.values().toArray();
    }

    public Reviews getValue(int key){
        return (Reviews) idMap.get(key);
    }

    /**
     * print the review object details
     * @param id
     */
    public String displayText(int id){
        System.out.println(idMap.get(id).toString());
        return "<br>" + idMap.get(id).toString() + "</br>";
    }


    /**
     * Returns size of the idMap
     * @return size
     */
    public int size(){
        int size = 0;
        for (Map.Entry<Integer, Object> entry: idMap.entrySet()) {
            size++;
        }
        return size;
    }

}

