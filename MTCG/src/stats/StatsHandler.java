package stats;

import Http.HttpStatus;
import db.getDBConnection;
import response.Response;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsHandler implements Response, getDBConnection,StatsHandlerInterface {

    public StatsHandler() {
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
            rs.close();
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