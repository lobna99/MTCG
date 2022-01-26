package transactions;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class PurchaseRequest implements HandleRequest, Response {

    PurchaseHandlerImpl purchaseHandler;

    public PurchaseRequest() {
        purchaseHandler = new PurchaseHandlerImpl();
    }

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "POST" -> {
                if (!purchaseHandler.purchasePack(request.getToken())) {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "ERR");
                } else {
                    respond.writeHttpResponse(HttpStatus.OK, "Purchase was succesful");
                }
            }
        }
    }
}
