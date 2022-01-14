package server;

import Http.HTTPRequest;
import Http.HttpStatus;
import response.Response;

import java.io.IOException;

public class PathHandlerImpl implements PathHandler, Response {

    public PathHandlerImpl(){
    }

    //----HANDLE EACH PATH AND CALL THE HANDLEREQUEST FUNCTION
    public void handlePath(HTTPRequest request) throws IOException {
        switch (request.getPath()){
            case "/users"->userRequest.handleRequest(Json ,request);
            case "/sessions"->loginRequest.handleRequest(Json,request);
            case "/packages"->packageRequest.handleRequest(Json,request);
            case "/transactions/packages"->purchaseRequest.handleRequest(Json,request);
            case "/cards"->cardRequest.handleRequest(Json,request);
            case "/deck","/deck?format=plain"->deckRequest.handleRequest(Json,request);
            case "/score"->scoreRequest.handleRequest(Json,request);
            case "/stats"->statsRequest.handleRequest(Json,request);
            case "/battles"->battleRequest.handleRequest(Json,request);
            default -> {if(request.getPath().contains("/users/"+request.getToken())) {userRequest.handleRequest(Json, request);
                       }else{ respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"cant access this user");}}
        }
    }
}
