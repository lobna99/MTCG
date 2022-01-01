package server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Server implements Runnable {

    private ServerSocket _listener;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void listen() {
        System.out.println("start server");

        try {
            this._listener = new ServerSocket(this.port, 5);
            System.out.println("http-server running at: http://localhost:" + this.port);
            while (true) {
                Socket s = this._listener.accept();
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
            return;
        }
    }

    @Override
    public void run() {
        try {
            _listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Server()));
        _listener = null;
        System.out.println("close server");
    }
}

