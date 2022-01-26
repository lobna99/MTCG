package battle;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class BattleRequest implements HandleRequest, Response {

    BattleHandlerImpl battleHandler;

    public BattleRequest() {
        battleHandler = new BattleHandlerImpl();
    }

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, InterruptedException, SQLException {
        switch (request.getMethod()) {
            case "POST" -> {
                battleHandler.setrdy(request.getToken());
                Thread.sleep(1000);
                respond.writeHttpResponse(HttpStatus.OK, battleHandler.checkforOpponent(request.getToken()));
            }
        }
    }
}
