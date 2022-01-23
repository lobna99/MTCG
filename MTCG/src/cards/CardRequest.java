package cards;

import Http.HTTPRequest;
import Http.HttpStatus;
import Json.ParseJsonInterface;
import response.Response;
import server.HandleRequest;

import java.io.IOException;

public class CardRequest implements HandleRequest, Response {

    CardHandler getCards;

    public CardRequest() {
        getCards = new CardHandler();
    }

    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "GET" -> {
                if (request.getToken() != null) {
                    getCards.getAllCards(request.getToken());
                } else {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "no token");
                } ;
            }
        }
    }
}
