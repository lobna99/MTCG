package sessions;


import db.DBconnectionImpl;
import db.getDBConnection;
import org.codehaus.jackson.JsonNode;
import Http.HttpStatus;
import response.Response;
import user.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandlerImpl implements getDBConnection, LoginHandler {


    public LoginHandlerImpl() {

    }

    public boolean login(JsonNode node) throws SQLException {
        String name = node.get("Username").getValueAsText();
        String password = node.get("Password").getValueAsText();
        User logged = new User(name, password);
        return logged(logged);
    }

    public boolean logged(User user) throws SQLException {
        //------------CHECK IF USER EXISTS
        java.sql.Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
                    SELECT *
                    from "user"
                    WHERE username=?
                    and password=?
                """);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            result.close();
            statement.close();
            con.close();
            return true;

        } else {
            result.close();
            statement.close();
            con.close();
            return false;
        }
    }

    public boolean authorization(String user) throws SQLException {
        //check if username and token match
        java.sql.Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
                    SELECT *
                    from "user"
                    WHERE username=?
                """);
        statement.setString(1, user);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            result.close();
            statement.close();
            con.close();
            return true;

        } else {
            result.close();
            statement.close();
            con.close();
            return false;
        }
    }
}
