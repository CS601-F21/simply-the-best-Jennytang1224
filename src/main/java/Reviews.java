
import java.util.ArrayList;

/**
 * Reviews class that contains the information about each review object
 * and methods to access and modify the data
 */
public class Reviews {
    private int docID;
    private String review_id;
    private String user_id;
    private String business_id;
    private float stars;
    private int useful;
    private int funny;
    private int cool;
    private String text;
    private String date;


    /**
     * constructor for Review class
     * @param docID document ID
     * @param asin asin this product
     * @param reviewText review for this product
     */
    public Reviews(int docID, String review_id, String business_id, String text, int star, String date){
        this.docID = docID;
        this.review_id = review_id;
        this.business_id = business_id;
        this.text = text;
        this.stars = star;
        this.date = date;
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
    public String getReviewId() {
        return review_id;
    }


    /**
     * Returns review text of the review
     * @return review text
     */
    public String getReviewText() {
        return text;
    }


    public String getBusiness_id(){
        return business_id;
    }


    public float getStars(){
        return stars;
    }

    /**
     * display the info of the review
     * @return review string
     */
    public String toString(){
        return "docID: " + docID + ", "
                + "reviewID: " + review_id + ", "
                + "businessID: " + business_id + ", "
                + "text: " + text + ", "
                + "stars: " + stars + ", "
                + "date: " + date;
    }
}
