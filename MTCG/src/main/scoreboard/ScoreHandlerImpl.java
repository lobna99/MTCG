package scoreboard;

import db.DBconnectionImpl;
import db.getDBConnection;
import response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreHandlerImpl implements getDBConnection, Response, ScoreHandler {

    public ScoreHandlerImpl() {
    }

    public String showScoreboard() throws IOException, SQLException {
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                    SELECT elo,username,won,lost,ratio
                    from users
                    ORDER BY elo
                    DESC ;
                """);
        ResultSet rs = statement.executeQuery();
        String response = "";
        while (rs.next()) {
            int elo = rs.getInt("elo");
            int wins = rs.getInt("won");
            int loses = rs.getInt("lost");
            float ratio = rs.getFloat("ratio");
            String username = rs.getString("username");
            response += "{\"Username\":\"" + username + "\",\" ELO\":\"" + elo + "\",\"Won\":\"" + wins + "\",\"Lost\":\"" + loses + "\",\"Ratio\":\"" + ratio + "\"}\n";
        }
        rs.close();
        statement.close();
        con.close();
        return response;
    }

}
