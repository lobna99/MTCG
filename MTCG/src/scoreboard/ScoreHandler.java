package scoreboard;

import db.DBconnection;
import server.HttpStatus;
import server.Responsebuilder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreHandler {

    private Responsebuilder respond = Responsebuilder.getInstance();
    private DBconnection Connection;

    public ScoreHandler() {
        Connection = DBconnection.getInstance();
    }


    public void getScore(String user) {
        PreparedStatement statement = null;
        try {
            statement = Connection.getConnection().prepareStatement("""
                        SELECT elo,won,lost
                        from users
                        WHERE username=?
                    """);
            statement.setString(1, user);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int elo = rs.getInt("elo");
                int wins = rs.getInt("won");
                int loses = rs.getInt("lost");
                String response = "{\"ELO\":\"" + elo + "\",\"Won\":\""+wins+"\",\"Lost\":\""+loses+"\"}";
                try {
                    respond.writeHttpResponse(HttpStatus.OK, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showScoreboard(){
        PreparedStatement statement = null;
        try {
            statement = Connection.getConnection().prepareStatement("""
                        SELECT elo,username
                        from users
                        ORDER BY elo
                        DESC ;
                    """);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int elo = rs.getInt("elo");
                String username = rs.getString("username");
                String response = "{\"Username\":\""+username+"\",\"ELO\":\"" + elo + "\"}";
                try {
                    respond.writeHttpResponse(HttpStatus.OK, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void win(String user){
        try {
            PreparedStatement inBattle = Connection.getConnection().prepareStatement("""
                    UPDATE users
                        SET won=won+1,elo=elo+3
                        WHERE username=?
                    """);
            inBattle.setString(1,user);
            inBattle.execute();
            inBattle.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void lose(String user){
        try {
            PreparedStatement inBattle = Connection.getConnection().prepareStatement("""
                    UPDATE users
                        SET lost=lost+1,elo=elo-5
                        WHERE username=?
                    """);
            inBattle.setString(1,user);
            inBattle.execute();
            inBattle.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
