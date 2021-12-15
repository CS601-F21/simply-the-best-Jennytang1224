package HTTP;

/**
 * A helper class to store various constants used for the HTTP server.
 */
public class HttpConstants {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String VERSION = "HTTP/1.0";

    public static final String OK = "200 OK";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String NOT_ALLOWED = "405 Method Not Allowed";
    public static final String BAD_REQUEST = "400 Bad HTTP.Request";
    public static final String INTERNAL_SERVER_ERROR = "500 Internal Server Error";
    public static final String HTTP_VERSION_NOT_SUPPORTED = "505 Http version not supported";


    public static final String CONTENT_LENGTH = "Content-Length:";
    public static final String CONNECTION_CLOSE = "Connection: close";

    public static final String FILE_PATH = "/files";


    public static final String NOT_FOUND_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Resource not found</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>The resource you are looking for was not found.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";


    public static final String NOT_ALLOWED_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Method not allowed</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>The method you are using is not allowed.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String BAD_REQUEST_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Bad request</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>This is a bad request</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";


    public static final String HTTP_VERSION_NOT_SUPPORTED_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>version not supported</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>This HTTP version is not allowed</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";
}
