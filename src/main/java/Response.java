import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * response class
 */
public class Response {
    String result;
    String HTTPStatusCode;
    PrintWriter writer;

    /**
     * constructor
     */
    public Response(String result){
        this.result = result;
    }

    public Response(){

    }


    /**
     * generate xhtml
     * @param writer
     * @param header
     */
    public void returnXHTML(PrintWriter writer, String header){
        StringBuffer xhtml = new StringBuffer();
        xhtml.append(header);
//        String[] lst = result.split(" ");
//        String term = lst[6];
        xhtml.append("<h3>Top 3 Restaurants in Portland: </h3>\n");
        xhtml.append("<ul>\n");
        xhtml.append(result);
        xhtml.append("</ul>\n");
        xhtml.append(HTTPServerConstants.PAGE_FOOTER);
        try{

            if (HtmlValidator.isValid(xhtml.toString())) {
                ServerUtils.send200(writer);
                writer.println(xhtml.toString());
            }
        } catch(ParserConfigurationException | IOException | SAXException e){
            ServerUtils.send400(writer);
            return;
        }
    }

    public void setHTTPStatusCode(String statusCode){
        this.HTTPStatusCode = statusCode;
    }

    public String getHTTPStatusCode() {
        return HTTPStatusCode;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
