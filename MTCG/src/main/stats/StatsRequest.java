package stats;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class StatsRequest implements HandleRequest, Response {

    StatsHandlerImpl statsHandler;

    public StatsRequest() {
        statsHandler = new StatsHandlerImpl();
    }

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "GET" -> respond.writeHttpResponse(HttpStatus.OK, statsHandler.getScore(request.getToken()));
        }
    }
}
