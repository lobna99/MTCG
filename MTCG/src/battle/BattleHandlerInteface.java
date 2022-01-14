package battle;

import response.Responsebuilder;

public interface BattleHandlerInteface {


    final Battle battle = Battle.getInstance();
    final Responsebuilder respond = Responsebuilder.getInstance();

    public void checkforOpponent(String user);
    public void setrdy(String user);
    public void inBattle(String user);
    public void resetBio(String user);

}
