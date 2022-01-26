package sessions;

import org.codehaus.jackson.JsonNode;
import user.User;

import java.sql.SQLException;

public interface LoginHandler {
    public boolean login(JsonNode node) throws SQLException;

    public boolean logged(User user) throws SQLException;
}
