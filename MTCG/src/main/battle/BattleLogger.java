package battle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BattleLogger {
    private static BattleLogger OBJ = null;
    private StringBuilder Logger;

    public static BattleLogger getInstance() {
        if (OBJ == null)
            OBJ = new BattleLogger();

        return OBJ;
    }

    private BattleLogger() {
        Logger = new StringBuilder("Log: \n");
    }

    //LOG BATTLE
    public void log(String message) {
        DateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now=new Date();
        Logger.append(time.format(now)+" "+message);
    }

    public String getLog() {
        return Logger.toString();
    }
}
