package server;
import java.net.Socket;

public class RequestHandler {
    private Socket socket;


    public RequestHandler(Socket s){
        this.socket=s;

    }

}
