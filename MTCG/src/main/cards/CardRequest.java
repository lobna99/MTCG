package cards;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class CardRequest implements HandleRequest, Response {

    CardHandlerImpl getCards;

    public CardRequest() {
        getCards = new CardHandlerImpl();
    }

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "GET" -> {
                if (request.getToken() != null) {
                    respond.writeHttpResponse(HttpStatus.OK, getCards.getAllCards(request.getToken()));
                } else {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "no token");
                }
            }
        }
    }
}
