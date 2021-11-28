import java.io.PrintWriter;

/**
 * A utility class to send server response messages.
 */
public class ServerUtils {

    /**
     * Send the status line of an HTTP 200 OK response.
     * @param writer
     */
    public static void send200(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.OK);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
    }

    /**
     * Send the status line of an HTTP 404 Not Found response.
     * @param writer
     */
    public static void send404(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.NOT_FOUND);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
        writer.println(HttpConstants.NOT_FOUND_PAGE);
    }

    /**
     * Send the status line of an HTTP 405 Method Not Allowed response.
     * @param writer
     */
    public static void send405(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.NOT_ALLOWED);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
        writer.println(HttpConstants.NOT_ALLOWED_PAGE);
    }

    /**
     * Send the status line of an HTTP 400 bad request
     * @param writer
     */
    public static void send400(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.BAD_REQUEST);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
        writer.println(HttpConstants.BAD_REQUEST_PAGE);
    }

    /**
     * Send the status line of an HTTP 505 version not supported
     * @param writer
     */
    public static void send505(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.HTTP_VERSION_NOT_SUPPORTED);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
        writer.println(HttpConstants.HTTP_VERSION_NOT_SUPPORTED_PAGE);
    }

    public static void sendStatus(String status, PrintWriter writer){
        if(status.equals("404")){
            ServerUtils.send404(writer);
        }
        else if(status.equals("400")){
            ServerUtils.send400(writer);
        }
        else if(status.equals("405")){
            ServerUtils.send405(writer);
        }
        else if(status.equals("505")){
            ServerUtils.send505(writer);
        }

    }

}
