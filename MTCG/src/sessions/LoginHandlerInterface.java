package sessions;

import org.codehaus.jackson.JsonNode;
import user.User;

import java.sql.SQLException;

public interface LoginHandlerInterface {
    public void login(JsonNode node);
    public boolean logged(User user) throws SQLException;
}
