package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket _listener;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        this.listen();
    }

    public void listen() {
        System.out.println("start server");

        try {
            _listener = new ServerSocket(this.port, 5);
            System.out.println("http-server running at: http://localhost:" + this.port);
            while (true) {
                Socket s = _listener.accept();
                RequestHandler requestHandler = new RequestHandler(s);
                Thread thread = new Thread(requestHandler);
                thread.start();
                /*URL url = new URL("http://localhost:10001/users");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestProperty("Content-Type", "application/json");
                System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
                 */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

