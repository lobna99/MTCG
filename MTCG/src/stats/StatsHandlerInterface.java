package stats;

public interface StatsHandlerInterface {
    public void lose(String user);
    public void win(String user);
    public void getScore(String user);
}
