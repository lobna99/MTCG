package user;

import db.getDBConnection;
import org.codehaus.jackson.JsonNode;
import Http.HttpStatus;
import response.Response;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler implements Response, getDBConnection {


    public UserHandler() {
    }
    public void registration(JsonNode node) throws IOException {
        String name= node.get("Username").getValueAsText();
        String password= node.get("Password").getValueAsText();
        User newUser=new User(name,password);
        try {
            insertUser(newUser);
            respond.writeHttpResponse(HttpStatus.CREATED,"User created");
        } catch (SQLException e) {
            respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"User already exists");
        }

    }
    public void insertUser(User newUser) throws SQLException{
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                             INSERT INTO users
                             (username,password,ELO,coins)
                             VALUES (?,?,?,?);   
                         """);
        statement.setString(1, newUser.getUsername());
        statement.setString(2, newUser.getPassword());
        statement.setInt(3, newUser.getELO());
        statement.setInt(4, newUser.getCoins());
        statement.execute();
        statement.close();
    }
    public void getUserData(String user){
            PreparedStatement statement = null;
            try {
                statement = Connection.getConnection().prepareStatement("""
                             SELECT *
                             from users
                             WHERE username=?
                         """);
                statement.setString(1, user);
                ResultSet rs=statement.executeQuery();
                while(rs.next()){
                    String username=rs.getString("Username");
                    String name=rs.getString("name");
                    String bio=rs.getString("bio");
                    String image=rs.getString("image");
                    int elo=rs.getInt("elo");
                    int coins=rs.getInt("coins");
                    String response="{\"Username\":\""+username+"\",\"Name\":\""+name+"\",\"ELO\":\""+elo+"\",\"Coins\":\""+coins+"\",\"Bio\":\""+bio+"\",\"Image\":\""+image+"\"}";
                    try {
                        respond.writeHttpResponse(HttpStatus.OK,response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                rs.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void updateUser(String user,JsonNode node){
        PreparedStatement statement = null;
        try {
            statement = Connection.getConnection().prepareStatement("""
                                UPDATE users
                                 SET bio=?,name=?,image=?
                                 WHERE username=?;
                             """);
            statement.setString(1,node.get("Bio").getValueAsText());
            statement.setString(2,node.get("Name").getValueAsText());
            statement.setString(3,node.get("Image").getValueAsText());
            statement.setString(4,user);
            statement.execute();
            statement.close();
            respond.writeHttpResponse(HttpStatus.OK,"user updated");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
