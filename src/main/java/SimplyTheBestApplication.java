import HTTP.HTTPServerConstants;
import HTTP.HttpServer;
import HTTP.ReviewSearchHandler;
import YelpSearch.UI;

import java.util.ArrayList;
import java.util.List;

public class SimplyTheBestApplication {

    public static void main(String[] args) {
        int port = 8081;
        HttpServer server = new HttpServer(port);
        List<String> files = new ArrayList<>();

        files.add(HTTPServerConstants.BUSINESSFILE);
        files.add(HTTPServerConstants.REVIEWFILE);
        UI ui = new UI(files);
        ui.processFiles();

        server.addMapping("/yelpsearch", new ReviewSearchHandler(ui));
        server.startup();
    }
}
