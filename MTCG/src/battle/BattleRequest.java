package battle;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class BattleRequest implements HandleRequest {

    BattleHandler battleHandler;

    public BattleRequest(){battleHandler = new BattleHandler();}

    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "POST"->battleHandler.checkforOpponent(request.getToken());
        }
    }
}
