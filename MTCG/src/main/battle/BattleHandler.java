package battle;

import response.Responsebuilder;

import java.io.IOException;
import java.sql.SQLException;

public interface BattleHandler {


    final BattleImpl battle = new BattleImpl();
    final Responsebuilder respond = Responsebuilder.getInstance();

    public String checkforOpponent(String user) throws SQLException, IOException;

    public boolean setrdy(String user) throws SQLException;

    public void inBattle(String user) throws SQLException;

    public void resetBio(String user) throws SQLException;

}
