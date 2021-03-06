package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements Server {

    private ServerSocket _listener;
    private int port;
    private ExecutorService pool = Executors.newSingleThreadExecutor();

    public ServerImpl(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        this.listen();
    }

    public void listen() throws IOException {
            System.out.println("start server");
            _listener = new ServerSocket(this.port, 5);
            System.out.println("http-server running at: http://localhost:" + this.port);
            while (true) {
                Socket s = _listener.accept();//listen for clients
                RequestHandlerImpl requestHandler = new RequestHandlerImpl(s);
                pool.execute(requestHandler);//execute thread pool
            }
    }
}

