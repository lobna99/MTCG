package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerImpl implements Server {

    private ServerSocket _listener;
    private int port;

    public ServerImpl(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        this.listen();
    }

    public void listen() {
        System.out.println("start server");
        try {
            _listener = new ServerSocket(this.port, 5);
            System.out.println("http-server running at: http://localhost:" + this.port);
            while (true) {
                Socket s = _listener.accept();
                RequestHandlerImpl requestHandler = new RequestHandlerImpl(s);
                Thread thread = new Thread(requestHandler);
                thread.start();
                clients.add(requestHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.out.println("Closing on port " + _listener.getLocalPort() + "...");
        try {
            _listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

