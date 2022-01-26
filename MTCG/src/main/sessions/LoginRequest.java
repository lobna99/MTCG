package sessions;

import Http.HTTPRequestImpl;
import Http.HttpStatus;
import Json.ParseJson;
import response.Response;
import server.HandleRequest;

import java.io.IOException;
import java.sql.SQLException;

public class LoginRequest implements HandleRequest, Response {
    LoginHandlerImpl loginHandler;

    public LoginRequest() {
        loginHandler = new LoginHandlerImpl();
    }

    @Override
    public void handleRequest(ParseJson node, HTTPRequestImpl request) throws IOException, SQLException {
        switch (request.getMethod()) {
            case "POST" -> {
                if (loginHandler.login(node.getJsonnode(request.getContent()))) {
                    respond.writeHttpResponse(HttpStatus.OK, "User logged in");
                } else {
                    respond.writeHttpResponse(HttpStatus.NOT_FOUND, "User not found");
                }
            }
        }
    }
}
