package trading;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class TradingRequest implements HandleRequest, Response {

    private TradingHandlerImpl tradingHandler = new TradingHandlerImpl();

    public TradingRequest() {

    }

    ;

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, InterruptedException, SQLException {
        String[] pathsplit = request.getPath().split("/", -2);
        switch (request.getMethod()) {
            case "GET" -> respond.writeHttpResponse(HttpStatus.OK, tradingHandler.getTrades());
            case "POST" -> {
                if (pathsplit.length > 2) {
                    if (!tradingHandler.trade(pathsplit[2], node.getJsonnode(request.getContent()), request.getToken())) {
                        respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "Cant trade with yourself");
                    }
                    ;
                } else {
                    if (tradingHandler.postTrade(node.getJsonnode(request.getContent()), request.getToken())) {
                        respond.writeHttpResponse(HttpStatus.CREATED, "Trading deal created");
                    } else {
                        respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "Card is in deck");
                    }
                }
            }
            case "DELETE" -> {
                tradingHandler.deleteTrade(request.getToken(), pathsplit[2]);
                respond.writeHttpResponse(HttpStatus.OK, "Trade deleted");
            }
        }
    }
}
