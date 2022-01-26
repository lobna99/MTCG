package sessions;

import Json.ParseJsonImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginHandlerImplTest {

    LoginHandlerImpl loginHandler=new LoginHandlerImpl();
    ParseJsonImpl parseJson=new ParseJsonImpl();

    @Test
    void login() {
        String content="{\"Username\":\"blab\", \"Password\":\"halo\"}";
        assertDoesNotThrow(()->assertFalse(loginHandler.login(parseJson.getJsonnode(content))));
    }
    @Test
    void authtest(){
        assertDoesNotThrow(()->assertFalse(loginHandler.authorization("blab")));
    }

}