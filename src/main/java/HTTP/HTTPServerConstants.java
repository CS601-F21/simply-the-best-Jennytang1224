package HTTP;

/**
 * A helper class to maintain constants used for the EchoServer example.
 */
public class HTTPServerConstants {

    public static final String REVIEW_SEARCH = "/simplythebest";
    public static final String REVIEWFILE = "data/yelp_academic_dataset_review_food.json";
    public static final String BUSINESSFILE = "data/yelp_academic_dataset_business_food.json";


    public static final String REVIEW_SEARCH_PAGE_HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>YelpSearch</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";

    public static final String PAGE_HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "<center>" + "<img src='https://i.ibb.co/K63f3mG/Screen-Shot-2021-12-14-at-10-26-13-PM.png'" +
            " alt='Screen-Shot-2021-12-14-at-10-26-13-PM' border='0'></img></center>" +
            "</head>" +
            "<body style=\"background-color:#E45565\">";

    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";


    public static final String WELCOME_MSG_PART1 = "<br><center><em><h1 style=\"color: white; font-size:60px; -webkit-text-stroke-width: 1px;\n" +
            "\">Find the best </h1></em></center><br>";

    public static final String WELCOME_MSG_PART2 = "<br><center><em><h1 style=\"color: white; font-size:60px; -webkit-text-stroke-width: 1px;\n" +
            "\">in Portland</h1></em></center>";

    public static final String RESULT_MSG_PART1 = "<br><center><em><h1 style=\"color: white; font-size:50px; -webkit-text-stroke-width: 1px;\n" +
            "\">Top ";

    public static final String RESULT_MSG_PART2 = " Place(s) with Best '";

    public static final String RESULT_MSG_PART3 = "' in Portland: </h1></br></h1></em></center>";



    public static final String GET_REVIEW_SEARCH_PAGE = PAGE_HEADER + REVIEW_SEARCH_PAGE_HEADER +
            WELCOME_MSG_PART1 +
            "<center><form action=\"/simplythebest\" method=\"post\">\n" +
            "  <input font-size: inherit;type=\"text\" id=\"query\" name=\"query\"/><br/>\n" +
            WELCOME_MSG_PART2 +
            "  <input type=\"submit\" value=\"Let's go\"/>\n" +
            "</form></center>" +
            PAGE_FOOTER;

    public static final String GET_BUTTON = "<center><a style=\"color:#ffffff;\" href=\"/simplythebest\">Search More</a></center>";

}
