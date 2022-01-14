import server.ServerImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ServerImpl server = new ServerImpl(10001);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
