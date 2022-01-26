package user;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class UserRequest implements HandleRequest, Response {

    UserHandler userHandler;

    public UserRequest() {
        userHandler = new UserHandler();
    }

    //handle all possible User requests
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "POST" -> {
                if (userHandler.registration(node.getJsonnode(request.getContent()))) {
                    respond.writeHttpResponse(HttpStatus.CREATED, "User created");
                } else {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "User already exists");
                }
            }
            case "GET" -> {
                if (request.getPath().contains(request.getToken())) {
                    respond.writeHttpResponse(HttpStatus.OK, userHandler.getUserData(request.getToken()));
                } else {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "cant access this user");
                }
            }
            case "PUT" -> {
                if (request.getPath().contains(request.getToken())) {
                    if (userHandler.updateUser(request.getToken(), node.getJsonnode(request.getContent()))) {
                        respond.writeHttpResponse(HttpStatus.OK, "user updated");
                    }
                } else {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "cant access this user");
                }
            }
        }
    }
}
