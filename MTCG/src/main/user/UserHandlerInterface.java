package user;

import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.sql.SQLException;

public interface UserHandlerInterface {
    public void getUserData(String user);

    public void updateUser(String user, JsonNode node);

    public void registration(JsonNode node) throws IOException;

    public void insertUser(User newUser) throws SQLException;
}
