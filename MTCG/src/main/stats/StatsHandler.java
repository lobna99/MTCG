package stats;

import java.io.IOException;
import java.sql.SQLException;

public interface StatsHandler {
    public int getELO(String user) throws SQLException;

    public int calculateElo(int elo1, int elo2, double r);

    public void updateStats(String user, int lost, int won, int elo, double ratio) throws SQLException;

    public String getScore(String user) throws SQLException, IOException;
}
