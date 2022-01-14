package scoreboard;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class ScoreRequest implements HandleRequest {

    ScoreHandler scoreHandler;
    public ScoreRequest(){
        scoreHandler=new ScoreHandler();
    }
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "GET"->scoreHandler.showScoreboard();
        }
    }
}
