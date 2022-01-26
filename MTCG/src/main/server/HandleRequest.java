package server;

import Http.HTTPRequestImpl;
import Json.ParseJson;

import java.io.IOException;
import java.sql.SQLException;

public interface HandleRequest {
    //inteface for all request handling classes
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, InterruptedException, SQLException;

}
