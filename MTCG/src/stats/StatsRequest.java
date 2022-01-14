package stats;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class StatsRequest implements HandleRequest {

    StatsHandler statsHandler;
    public StatsRequest(){
        statsHandler=new StatsHandler();
    }
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "GET"->statsHandler.getScore(request.getToken());
        }
    }
}
