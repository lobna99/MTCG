package cards;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class CardRequest implements HandleRequest {

    CardHandler getCards;

    public CardRequest(){getCards = new CardHandler();}

    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "GET"->getCards.getAllCards(request.getToken());
        }
    }
}
