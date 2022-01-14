package scoreboard;
import Http.HttpStatus;
import db.getDBConnection;
import response.Response;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreHandler implements getDBConnection, Response,ScoreHandlerInterface {

    public ScoreHandler() {
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
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
