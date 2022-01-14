package packages;

import cards.CardHandler;
import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class PackageRequest implements HandleRequest {

    PackageHandler packageHandler;
    CardHandler cardHandler;
    public PackageRequest(){
        packageHandler=new PackageHandler();
        cardHandler=new CardHandler();
    }
    //------HANDLE PACKAGE REQUESTS
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "POST"->cardHandler.extractnewcards(node.getJsonnode(request.getContent()), packageHandler.addPackage());
        }
    }
}
