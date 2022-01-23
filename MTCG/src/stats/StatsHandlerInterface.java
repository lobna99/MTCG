package stats;

public interface StatsHandlerInterface {
    public int getELO(String user);
    public int calculateElo(int elo1,int elo2,double r);
    public void updateStats(String user,int lost,int won, int elo);
    public void getScore(String user);
}
