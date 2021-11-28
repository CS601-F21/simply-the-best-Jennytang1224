import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * this class is a server!
 */
public class HttpServer {

    private volatile boolean running;
    private int port;
    private Map<String, Handler> pathToHandlerMap;
    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);
    private volatile boolean threadRunning;
    private ExecutorService threadPool;


    /**
     * Constructor: needs port on which to listen.
     * @param port
     */
    public HttpServer(int port) {
        this.port = port;
        this.running = true;
        this.threadRunning = true;
        this.threadPool = Executors.newFixedThreadPool(3);
        this.pathToHandlerMap = new ConcurrentHashMap<>();
    }


    /**
     * add the path and handler
     * @param path
     * @param handler
     */
    public void addMapping(String path, Handler handler){
       this.pathToHandlerMap.put(path, handler);
    }

    /**
     * validateRequestLine
     * @param requestLineParts
     * @return boolean
     */
    public boolean validateRequestLine(String[] requestLineParts){
        if((requestLineParts[0].equals("POST") || requestLineParts[0].equals("GET"))
                && this.pathToHandlerMap.containsKey(requestLineParts[1])
                && requestLineParts[2].equals("HTTP/1.1")){
            return true;
        }
        return false;
    }

    /**
     * check if request line contains three parts
     * @param requestLineParts
     * @return boolean
     */
    public boolean containsThreeSubstrings(String[] requestLineParts){
        if(requestLineParts.length == 3) {
            return true;
        }
        return false;
    }

    /**
     * shutdown threadpool
     */
    public synchronized void shutdownThreadPool(){
        this.threadRunning = false;
        this.running = false;
        this.threadPool.shutdown();
        try {
            this.threadPool.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Thread pool termination interrupted: " + e);
        }
    }

    /**
     * Server starts listening...
     */
    public synchronized void startup() {
        try (ServerSocket serve = new ServerSocket(port)) {
            LOGGER.info("server is listening on port " + port);

            while(running && threadRunning) {
                LOGGER.info("Waiting for client's connection...");

                Socket socket = serve.accept();
                LOGGER.info("Got a new connection from " + socket.getInetAddress());

//                this.threadPool.execute(() -> {
                    try (
                            BufferedReader instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter writer = new PrintWriter(socket.getOutputStream())
                    ) {
                        Response response = new Response();
                        response.setWriter(writer);
                        String requestLine = instream.readLine();
                        String[] requestLineParts = requestLine.split("\\s");

                        String method = requestLineParts[0];
                        String path = requestLineParts[1];
                        String version = requestLineParts[2];

                        if (!containsThreeSubstrings(requestLineParts)){
                            ServerUtils.send400(writer);
                            return;
                        }
                        if(!validateRequestLine(requestLineParts)) {
                            ServerUtils.send404(writer);
                            return;
                        }

                        Request request = new Request(instream, writer);
                        request.setMethod(method);
                        request.setPath(path);
                        request.setVersion(version);
                        request.setInstream(instream);
                        if(pathToHandlerMap.get(path)!= null) {
                            pathToHandlerMap.get(path).handle(request, response);
                        }

                    }
                    catch (IOException ioe) {
                        ioe.printStackTrace();
                        shutdownThreadPool();
                    }
//                });
            }

        } catch(IOException ioe) {
            ioe.printStackTrace();
            shutdownThreadPool();
        }
    }
}
