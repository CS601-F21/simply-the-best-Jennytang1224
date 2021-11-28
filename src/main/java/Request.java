import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * request class
 */
public class Request {
    private int contentLength;
    private BufferedReader instream;
    private static final Logger LOGGER = LogManager.getLogger(Request.class);
    private PrintWriter writer;
    private String path;
    private String method;
    private String version;

    /**
     * constructor
     */
    public Request(BufferedReader instream, PrintWriter writer){
        this.instream = instream;
        this.writer = writer;
    }

    /**
     * get request body
     * @return request body
     */
    public String getBody()  {
        String bodyValue = "";
        char[] bodyArr = new char[contentLength];
        try {
            instream.read(bodyArr, 0, bodyArr.length);
            String body = new String(bodyArr);
            LOGGER.info("Message body: " + body);

            if(!validateRequestBody(body)){
                ServerUtils.send400(writer); //bad request
                return null;
            }
            bodyValue = URLDecoder.decode(body, StandardCharsets.UTF_8.toString());
            LOGGER.info("Message body value: " + bodyValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodyValue;
    }


    /**
     * validate request body
     * @param body
     * @return ture or false
     */
    public boolean validateRequestBody(String body){
        if(!body.contains("=")){
            return false;
        }
        String[] requestBody = body.split("=");
        if(//(!requestBody[0].equals("asin") && path.equals("/find"))
//                || (!requestBody[0].equals("message") &&  path.equals("/slackbot"))
               // ||
        (!requestBody[0].equals("query") && path.equals("/reviewsearch"))){
            return false;
        }
        return true;
    }

    /**
     * validate request
     * @param method
     * @param path
     * @param version
     * @return status code
     */
    public String validateStatus(String method, String path, String version){
        if (!method.equals(HttpConstants.GET) && !method.equals(HttpConstants.POST)) {
            return "405"; // method not allowed
        }
        else if (!path.equals(HTTPServerConstants.REVIEW_SEARCH)
//                && !path.equals(HTTPServerConstants.FIND)
//                && !path.equals(HTTPServerConstants.SLACKBOT)
        )
        {
            return "404";// not found
        }
        else if(!version.equals("HTTP/1.1")){
            return "500"; // http version not supported
        }
        else {
            return "200"; //OK
        }
    }

    public BufferedReader getInstream() {
        return instream;
    }

    public void setInstream(BufferedReader instream) {
        this.instream = instream;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
