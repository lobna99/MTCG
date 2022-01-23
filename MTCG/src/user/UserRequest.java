package user;

import Http.HTTPRequest;
import Http.HttpStatus;
import Json.ParseJsonInterface;
import response.Response;
import server.HandleRequest;

import java.io.IOException;

public class UserRequest implements HandleRequest, Response {

    UserHandler userHandler;

    public UserRequest() {
        userHandler = new UserHandler();
    }

    //handle all possible User requests
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "POST" -> userHandler.registration(node.getJsonnode(request.getContent()));
            case "GET" -> {
                if (request.getPath().contains(request.getToken())) {
                    userHandler.getUserData(request.getToken());
                } else {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "cant access this user");
                }
                ;
            }
            case "PUT" -> {
                if (request.getPath().contains(request.getToken())) {
                    userHandler.updateUser(request.getToken(), node.getJsonnode(request.getContent()));
                } else {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "cant access this user");
                }
            }
        }
    }
}
