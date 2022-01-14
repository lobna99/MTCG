package sessions;

import Http.HTTPRequest;
import Json.ParseJsonInterface;
import server.HandleRequest;

import java.io.IOException;

public class LoginRequest implements HandleRequest {
    LoginHandler loginHandler;

    public LoginRequest(){
        loginHandler=new LoginHandler();
    }
    public void handleRequest(ParseJsonInterface node, HTTPRequest request) throws IOException {
        switch (request.getMethod()) {
            case "POST" -> loginHandler.login(node.getJsonnode(request.getContent()));
        }
    }
}
