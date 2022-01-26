package user;

import Json.ParseJsonImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {
    UserHandler testuser=new UserHandler();
    ParseJsonImpl parseJson=new ParseJsonImpl();

    @org.junit.jupiter.api.Test
    void registration(){
    String content="{\"Username\":\"blabla\", \"Password\":\"hallo\"}";
    assertDoesNotThrow(()->testuser.registration(parseJson.getJsonnode(content)));
    }
    @Test
    void registrateWithoutPass(){
        String content2="{\"Username\":\"blabla\"}";
        assertThrows(NullPointerException.class,()->testuser.registration(parseJson.getJsonnode(content2)));
    }
    @Test
    void updateUser(){
        assertDoesNotThrow(()->assertTrue(testuser.updateUser("blabla",parseJson.getJsonnode("{\"Name\": \"Hoax\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}"))));
    }
    @Test
    void updateUserNotExists(){
        assertDoesNotThrow(()->assertFalse(testuser.updateUser("hallo",parseJson.getJsonnode("{\"Name\": \"Hoax\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}"))));
    }
    @Test
    void notenoughParameters(){
        assertThrows(NullPointerException.class,()->assertFalse(testuser.updateUser("blabla",parseJson.getJsonnode("{\"Name\": \"Hoax\", \"Image\": \":-)\"}"))));
    }

}