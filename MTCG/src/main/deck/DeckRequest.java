package deck;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class DeckRequest implements HandleRequest, Response {
    DeckHandlerImpl deckHandler;

    public DeckRequest() {
        deckHandler = new DeckHandlerImpl();
    }

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "PUT" -> {
                if (!deckHandler.configureDeck(node.getJsonnode(request.getContent()), request.getToken())) {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "not enough cards");
                } else {
                    respond.writeHttpResponse(HttpStatus.OK, "Deck configurated");
                }
            }
            case "GET" -> respond.writeHttpResponse(HttpStatus.OK, deckHandler.getDeck(request.getToken(), request.getPath()));
        }
    }
}
