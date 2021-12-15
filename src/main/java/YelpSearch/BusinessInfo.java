package YelpSearch;

/**
 * YelpSearch.Reviews class that contains the information about each review object
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
        return "<h1>" + name + "</h1>"
                + "<h3>" + "Address: " + address + "</h3>"
                + "<h3>" + "City: " + city + "</h3>"
                + "<h3>" + "State: " + state + "</h3>"
                + "<h3>" + "Postal: " + postal_code + "</h3>" ;
    }
}
