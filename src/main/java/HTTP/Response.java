package HTTP;

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
        xhtml.append("<ul><center>" + result + "</center></ul>");
        xhtml.append(HTTPServerConstants.GET_BUTTON);
        xhtml.append(HTTPServerConstants.PAGE_FOOTER);
        ServerUtils.send200(writer);
        writer.println(xhtml.toString());
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
