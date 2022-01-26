package server;

import java.io.IOException;
import java.util.ArrayList;

public interface Server {

    public void listen() throws IOException;

    public void start() throws IOException;
}
