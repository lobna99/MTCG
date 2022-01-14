package transactions;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class PurchaseRequest implements HandleRequest {

    PurchaseHandler purchaseHandler;

    public PurchaseRequest(){
        purchaseHandler=new PurchaseHandler();
    }
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "POST" -> purchaseHandler.purchasePack(request.getToken());
        }
    }
}
