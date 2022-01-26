package server;

import java.io.IOException;
import java.util.ArrayList;

public interface Server {

    public void listen();

    public void start() throws IOException;
}
