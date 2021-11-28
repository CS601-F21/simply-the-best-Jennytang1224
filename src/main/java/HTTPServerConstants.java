

/**
 * A helper class to maintain constants used for the EchoServer example.
 */
public class HTTPServerConstants {

    public static final String REVIEW_SEARCH = "/yelpsearch";
    public static final String REVIEWFILE = "data/yelp_academic_dataset_review_food.json";
    public static final String BUSINESSFILE = "data/yelp_academic_dataset_business_food.json";


    public static final String REVIEW_SEARCH_PAGE_HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>YelpSearch</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";


    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";

    public static final String GET_REVIEW_SEARCH_PAGE = REVIEW_SEARCH_PAGE_HEADER +
            "<form action=\"/yelpsearch\" method=\"post\">\n" +
            "  <label for=\"msg\">What food would you like to find in Portland:</label><br/>\n" +
            "  <input type=\"text\" id=\"query\" name=\"query\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>" +
            PAGE_FOOTER;

}
