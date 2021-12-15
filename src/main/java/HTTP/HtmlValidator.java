//package HTTP;
//
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import org.xml.sax.XMLReader;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import java.io.IOException;
//import java.io.StringReader;
//
///**
// * Helper class with method to determine if an HTML document is well formed.
// */
//public class HtmlValidator {
//
//    /**
//     * Returns true if the html is valid and false otherwise.
//     * Taken from Wellformed.java example from
//     * http://www.edankert.com/validate.html#Sample_Code
//     * @param html
//     * @return
//     */
//    public static boolean isValid(String html) throws ParserConfigurationException, SAXException, IOException{
//        boolean isHtmlValid = false;
//        try {
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            factory.setValidating(false);
//            factory.setNamespaceAware(true);
//            SAXParser parser = factory.newSAXParser();
//            XMLReader reader = parser.getXMLReader();
//            html = html.replaceAll( "&", "&amp;" );
//            reader.parse(new InputSource(new StringReader(html)));
//            isHtmlValid = true;
//        } catch (ParserConfigurationException | SAXException | IOException e) {
//            e.printStackTrace();
//            throw(e);
//        } finally {
//            return isHtmlValid;
//        }
//    }
///**/
//}
