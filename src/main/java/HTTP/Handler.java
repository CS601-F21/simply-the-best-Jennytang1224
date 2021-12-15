package HTTP;

/**
 * this is a handler interface
 * Each web application will implement a different set of handlers,
 * for example a search application will support a SearchHandler
 * and a chat application would have a ChatHandler.
 */
public interface Handler {
    /**
     * handle do post and do get
     */
    void handle(Request request, Response response);

    /**
     * updating something in server
     */
    void doPost(Request request, Response response);

    /**
     *  fetch something from server
     */
    void doGet(Response response);








}
