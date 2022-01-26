package scoreboard;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class ScoreRequest implements HandleRequest, Response {

    ScoreHandlerImpl scoreHandler;

    public ScoreRequest() {
        scoreHandler = new ScoreHandlerImpl();
    }

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "GET" -> respond.writeHttpResponse(HttpStatus.OK, scoreHandler.showScoreboard());
        }
    }
}
