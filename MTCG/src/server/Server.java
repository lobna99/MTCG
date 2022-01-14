package server;

import java.io.IOException;
import java.util.ArrayList;

public interface Server {

    final ArrayList<RequestHandlerImpl> clients=new ArrayList<RequestHandlerImpl>();

    public void close();
    public void listen();
    public void start() throws IOException;
}
