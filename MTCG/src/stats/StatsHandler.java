package stats;

import Http.HttpStatus;
import db.DBconnectionImpl;
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
            statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
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
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getELO(String user){
        try {
            PreparedStatement inBattle = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                        SELECT elo FROM users
                        WHERE username=?
                    """);
            inBattle.setString(1,user);
            ResultSet elos=inBattle.executeQuery();
            if(elos.next()){
                return elos.getInt(1);
            }
            elos.close();
            inBattle.close();
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int calculateElo(int elo1, int elo2, double r){
        int k=40;
        int Ea;
        if(elo1>=2400&&elo2>=2400){
            k=10;
        }
        Ea=1/(1+k^((elo2-elo1)/400));

        return (int) (elo1+k*(r-Ea));
    }
    public void updateStats(String user,int lost,int won, int elo){
        try {
            PreparedStatement inBattle = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                    UPDATE users
                        SET lost=?,won=?,elo=?
                        WHERE username=?
                    """);
            inBattle.setInt(1,lost);
            inBattle.setInt(2,won);
            inBattle.setInt(3,elo);
            inBattle.setString(4,user);
            inBattle.execute();
            inBattle.close();
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
