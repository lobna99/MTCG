package user;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class UserRequest implements HandleRequest {

    UserHandler userHandler;

    public UserRequest() {
        userHandler = new UserHandler();
    }

    //handle all possible User requests
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "POST" -> userHandler.registration(node.getJsonnode(request.getContent()));
            case "GET" -> userHandler.getUserData(request.getToken());
            case "PUT" -> userHandler.updateUser(request.getToken(), node.getJsonnode(request.getContent()));
        }
    }
}
