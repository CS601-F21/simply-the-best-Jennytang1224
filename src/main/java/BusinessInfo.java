import com.google.gson.annotations.Expose;

/**
 * Reviews class that contains the information about each review object
 * and methods to access and modify the data
 */
public class BusinessInfo {
    private int docID;
    private String business_id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private float stars;
    private int review_count;
    private String categories;

    /**
     * constructor for Review class
     * @param docID document ID
     * @param asin asin this product
     * @param reviewText review for this product
     */
    public BusinessInfo(int docID, String business_id, String name, String address, String city, String state, String postal_code){
        this.docID = docID;
        this.business_id = business_id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
    }


    /**
     * Returns docId of the review
     * @return docId
     */
    public int getDocID() {
        return docID;
    }


    /**
     * set docId of the review
     */
    public void setDocID(int docID) {
        this.docID = docID;
    }


    /**
     * Returns ASIN of the review
     * @return ASIN
     */
    public String getBusiness_id() {
        return business_id;
    }



    public String getBusinessCategories(){
        return categories;
    }



    /**
     * display the info of the review
     * @return review string
     */
    public String toString(){
        return "businessID: " + business_id + ", "
                + "name: " + name + ", "
                + "address: " + address + ", "
                + "city: " + city + ", "
                + "state: " + state + ", "
                + "postal: " + postal_code;
    }
}
