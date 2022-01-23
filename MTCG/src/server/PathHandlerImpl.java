package server;

import Http.HTTPRequest;
import Http.HttpStatus;
import response.Response;

import java.io.IOException;

public class PathHandlerImpl implements PathHandler, Response {

    public PathHandlerImpl(){
    }

    //----HANDLE EACH PATH AND CALL THE HANDLEREQUEST FUNCTION
    public void handlePath(HTTPRequest request) throws IOException, InterruptedException {
        String[] pathsplit=request.getPath().split("/",-2);
        switch (pathsplit[1]){
            case "users"->userRequest.handleRequest(Json ,request);
            case "sessions"->loginRequest.handleRequest(Json,request);
            case "packages"->packageRequest.handleRequest(Json,request);
            case "transactions"->purchaseRequest.handleRequest(Json,request);
            case "cards"->cardRequest.handleRequest(Json,request);
            case "deck","deck?format=plain"->deckRequest.handleRequest(Json,request);
            case "score"->scoreRequest.handleRequest(Json,request);
            case "stats"->statsRequest.handleRequest(Json,request);
            case "battles"->battleRequest.handleRequest(Json,request);
            //case "/tradings"->;//TODO: trading kack
        }
    }
}
