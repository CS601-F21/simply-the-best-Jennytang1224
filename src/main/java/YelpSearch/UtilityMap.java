package YelpSearch;

/**
 * YelpSearch.UtilityMap class is a data structure to manage YelpSearch.InvertedIndex, YelpSearch.IdMap and IdAsinMap.
 * its also a wrap to add/put methods for these three data structure for
 * encapsulation purpose when creating them in reading files
 */
public class UtilityMap {

    /**
     * add items to invertedindex
     * @param invertedIndex an invertedIndex map
     * @param obj review/qa object
     */
    public static void addToInvertedIndex(InvertedIndex invertedIndex, Object obj){
        invertedIndex.add(obj);
    }


    /**
     * add items to idMap
     * @param idMap an idMap
     * @param obj review/qa object
     */
    public static void addToIdMap(IdMap idMap, int docID, Object obj){
        idMap.put(docID, obj);
    }


    public static void addToBusinessIdMap(BusinessIdMap businessIdMap, String bizId, Object obj){
        businessIdMap.put(bizId, obj);
    }



}
