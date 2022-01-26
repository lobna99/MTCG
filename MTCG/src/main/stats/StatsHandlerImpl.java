package stats;

import db.DBconnectionImpl;
import db.getDBConnection;
import response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsHandlerImpl implements getDBConnection, StatsHandler {

    public StatsHandlerImpl() {
    }

    public String getScore(String user) throws SQLException, IOException {
        //get the whole score of user
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                    SELECT elo,won,lost,ratio
                    from "user"
                    WHERE username=?
                """);
        statement.setString(1, user);
        ResultSet rs = statement.executeQuery();
        String response = "";
        while (rs.next()) {
            int elo = rs.getInt("elo");
            int wins = rs.getInt("won");
            int loses = rs.getInt("lost");
            double ratio = rs.getDouble("ratio");
            response += "{\"ELO\":\"" + elo + "\",\"Won\":\"" + wins + "\",\"Lost\":\"" + loses + "\",\"Winratio\":\"" + ratio*100 + "% \"}\n";
        }
        rs.close();
        statement.close();
        con.close();
        return response;
    }

    //Get elo of user
    public int getELO(String user) throws SQLException {
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement inBattle = con.prepareStatement("""
                    SELECT elo FROM "user"
                    WHERE username=?
                """);
        inBattle.setString(1, user);
        ResultSet elos = inBattle.executeQuery();
        if (elos.next()) {
            int elo = elos.getInt(1);
            elos.close();
            inBattle.close();
            con.close();
            return elo;
        }
        elos.close();
        inBattle.close();
        con.close();
        return 0;
    }

    public int calculateElo(int elo1, int elo2, double r) {
        //calculate Elo every round
        int k = 40;
        int Ea;
        if (elo1 >= 2400 && elo2 >= 2400) {
            k = 10;
        }
        Ea = 1 / (1 + k ^ ((elo2 - elo1) / 400));

        return (int) (elo1 + k * (r - Ea));
    }

    public void updateStats(String user, int lost, int won, int elo, double ratio) throws SQLException {
        //update all stats
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement inBattle = con.prepareStatement("""
                UPDATE "user"
                    SET lost=?,won=?,elo=?,ratio=?
                    WHERE username=?
                """);
        inBattle.setInt(1, lost);
        inBattle.setInt(2, won);
        inBattle.setInt(3, elo);
        inBattle.setDouble(4, ratio);
        inBattle.setString(5, user);
        inBattle.execute();
        inBattle.close();
        con.close();
    }
}
