package server;

import Http.HTTPRequest;
import Json.ParseJsonInterface;

import java.io.IOException;

public interface HandleRequest {
    //inteface for all request handling classes
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException, InterruptedException;

}
