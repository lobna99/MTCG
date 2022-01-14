package battle;

public class BattleLogger {
    private static BattleLogger OBJ =null;
    private StringBuilder Logger;

    public static BattleLogger getInstance() {
        if (OBJ == null)
            OBJ = new BattleLogger();

        return OBJ;
    }
    private BattleLogger(){
        Logger=new StringBuilder("Log: \n");
    }
    public void log(String message){
        Logger.append(message);
    }
    public String getLog(){
        return Logger.toString();
    }
}
