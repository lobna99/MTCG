package packages;

import Http.HttpStatus;
import cards.CardHandlerImpl;
import Http.HTTPRequestImpl;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class PackageRequest implements HandleRequest, Response {

    PackageHandlerImpl packageHandler;
    CardHandlerImpl cardHandler;

    public PackageRequest() {
        packageHandler = new PackageHandlerImpl();
        cardHandler = new CardHandlerImpl();
    }

    //------HANDLE PACKAGE REQUESTS
    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "POST" -> {
                if (!cardHandler.extractnewcards(node.getJsonnode(request.getContent()), packageHandler.addPackage())) {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "ERR");
                }
            }
        }
    }
}
