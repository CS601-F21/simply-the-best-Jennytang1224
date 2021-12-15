package HTTP;

import YelpSearch.Search;
import YelpSearch.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is a class handle review search
 */
public class ReviewSearchHandler implements Handler{
    int contentLength = 0;
    UI ui;

    public ReviewSearchHandler(UI ui){
        this.ui = ui;
    }

    @Override
    public void handle(Request request, Response response) {
            BufferedReader instream = request.getInstream();
            String method = request.getMethod();
            String path = request.getPath();
            String version = request.getVersion();
            PrintWriter writer = response.writer;

            List<String> headers = new ArrayList<>();
            String header;
            try {
                while (!(header = instream.readLine()).isEmpty()) {
                    headers.add(header);
                    if (header.startsWith(HttpConstants.CONTENT_LENGTH)) {
                        String[] contentLengthParts = header.split("\\s");
                        contentLength = Integer.parseInt(contentLengthParts[1]);
                        request.setContentLength(contentLength);
                    }
                }

                String status = request.validateStatus(method, path, version);
                System.out.println("status:" + status);
                ServerUtils.sendStatus(status, writer);

                if (!status.equals("200")) {
                    return;
                }

                // if method is GET
                if (method.equals(HttpConstants.GET)) {
                    ServerUtils.send200(writer);
                    doGet(response);
                // if method is POST
                } else if (method.equals(HttpConstants.POST)){
                    doPost(request, response);
                    String headerStr = HTTPServerConstants.PAGE_HEADER + HTTPServerConstants.REVIEW_SEARCH_PAGE_HEADER;
                    response.returnXHTML(writer, headerStr);
                }
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
    }


    @Override
    public void doPost(Request request, Response response)  {
        String body = request.getBody();
        System.out.println("body: " + body);
        String term = body.substring(6); // query=xxx
        // read files
        String result = getSearchResult(term);
        System.out.println(result);
        response.setResult(result);
        response.setHTTPStatusCode("200");

    }


    /**
     * get search result
     * @param term
     * @return result
     */
    public String getSearchResult(String term){
        Search search = new Search(ui.getInvertedIndexRW(), ui.getIdMapRW(), ui.getIdMapBiz());
        return search.displayTopKBusiness(term, 3);
    }


    @Override
    public void doGet(Response response) {
        PrintWriter writer = response.writer;
        writer.println(HTTPServerConstants.GET_REVIEW_SEARCH_PAGE);

    }
}
