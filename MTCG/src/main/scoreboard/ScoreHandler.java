package scoreboard;


import java.io.IOException;
import java.sql.SQLException;

public interface ScoreHandler {
    public String showScoreboard() throws IOException, SQLException;
}
