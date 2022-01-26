package server;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import response.Response;
import sessions.LoginHandlerImpl;

import java.io.IOException;
import java.sql.SQLException;

public class PathHandlerImpl implements PathHandler, Response {

    private LoginHandlerImpl auth = new LoginHandlerImpl();

    public PathHandlerImpl() {
    }

    //----HANDLE EACH PATH AND CALL THE HANDLEREQUEST FUNCTION
    public void handlePath(HTTPRequestImpl request) throws IOException, InterruptedException, SQLException {
        String[] pathsplit = request.getPath().split("/", -2);
        if (auth.authorization(request.getToken()) || request.getPath().equals("/users") || request.getPath().equals("/sessions")) {
            switch (pathsplit[1]) {
                case "users" -> userRequest.handleRequest(Json, request);
                case "sessions" -> loginRequest.handleRequest(Json, request);
                case "packages" -> packageRequest.handleRequest(Json, request);
                case "transactions" -> purchaseRequest.handleRequest(Json, request);
                case "cards" -> cardRequest.handleRequest(Json, request);
                case "deck", "deck?format=plain" -> deckRequest.handleRequest(Json, request);
                case "score" -> scoreRequest.handleRequest(Json, request);
                case "stats" -> statsRequest.handleRequest(Json, request);
                case "battles" -> battleRequest.handleRequest(Json, request);
                case "tradings" -> tradingRequest.handleRequest(Json, request);
            }
        } else {
            respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "Authentication failed");
        }
    }
}
