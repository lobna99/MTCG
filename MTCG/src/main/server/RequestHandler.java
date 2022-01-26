package server;

import response.Responsebuilder;

import java.io.IOException;
import java.sql.SQLException;

public interface RequestHandler {
    Responsebuilder responsebuilder = Responsebuilder.getInstance();

    public void handlerequests() throws IOException, InterruptedException, SQLException;

    public void closeEverything();
}
