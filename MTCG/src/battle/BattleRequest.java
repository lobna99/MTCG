package battle;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class BattleRequest implements HandleRequest {

    BattleHandler battleHandler;

    public BattleRequest(){battleHandler = new BattleHandler();}

    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException, InterruptedException {
        switch (request.getMethod()) {
            case "POST"-> {
                battleHandler.setrdy(request.getToken());
                Thread.sleep(1000);
                battleHandler.checkforOpponent(request.getToken());
            }
        }
    }
}
