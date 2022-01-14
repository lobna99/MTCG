package deck;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class DeckRequest implements HandleRequest {
    DeckHandler deckHandler;

    public DeckRequest(){
        deckHandler=new DeckHandler();
    }
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "PUT"->deckHandler.configureDeck(node.getJsonnode(request.getContent()), request.getToken());
            case "GET"->deckHandler.getDeck(request.getToken(), request.getPath());
        }
    }
}
