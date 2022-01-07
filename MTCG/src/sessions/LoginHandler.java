package sessions;

import db.DBconnection;
import org.codehaus.jackson.JsonNode;
import server.HttpStatus;
import server.Responsebuilder;
import user.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler {
    private DBconnection Connection;

    public LoginHandler() {
        Connection=DBconnection.getInstance();
    }
    public void login(JsonNode node){
        Responsebuilder respond=Responsebuilder.getInstance();
        String name= node.get("Username").getValueAsText();
        String password= node.get("Password").getValueAsText();
        User logged=new User(name,password);
        try {
            if(logged(logged)){
                respond.writeHttpResponse(HttpStatus.OK,"User logged in");
            }else{
                respond.writeHttpResponse(HttpStatus.NOT_FOUND,"User not found");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public boolean logged(User user) throws SQLException {
        //------------CHECK IF USER EXISTS
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                         SELECT * 
                         from users 
                         WHERE username=? 
                         and password=?
                     """);
        statement.setString(1,user.getUsername());
        statement.setString(2,user.getPassword());
        ResultSet result=statement.executeQuery();
        if(result.next()){
            statement.close();
            return true;

        }else {
            statement.close();
            return false;
        }
    }
}
