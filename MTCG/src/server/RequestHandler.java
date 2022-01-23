package server;

import Http.HTTPRequest;
import response.Responsebuilder;

import java.io.IOException;

public interface RequestHandler {
    Responsebuilder responsebuilder = Responsebuilder.getInstance();
    public void handlerequests() throws IOException, InterruptedException;
    public void closeEverything();
}
