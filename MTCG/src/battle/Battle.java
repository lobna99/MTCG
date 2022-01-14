package battle;

import cards.Card;
import Http.HttpStatus;
import response.Response;
import response.Responsebuilder;
import stats.StatsHandler;

import java.io.IOException;

public class Battle implements BattleInterface, Response {
    private int Rounds;
    private static Battle OBJ =null;


    public static Battle getInstance() {
        if (OBJ == null)
            OBJ = new Battle();

        return OBJ;
    }
    private Battle() {
        Rounds = 100;
    }

    public String calculateDMG(Card first, Card second) {

        double dmg1 = first.getDmg();
        double dmg2 = second.getDmg();
        int elem1 = first.getElement().getElem();
        int elem2 = second.getElement().getElem();

        if (first.getName().contains("Spell") || second.getName().contains("Spell")) {
            if (elem1 - elem2 < 0 || elem1 - elem2 == 2) {
                dmg1 *= 2;
                dmg2 = dmg2 / 2;
            } else if (elem1 - elem2 > 0 || elem1 - elem2 == -2) {
                dmg1 = dmg1 / 2;
                dmg2 *= 2;
            }
        }
        if (dmg1 > dmg2) {
            return "Won";
        } else if (dmg1 < dmg2) {
            return "Lost";
        } else if (dmg1 == dmg2) {
            return "draw";
        }
        return null;
    }

    public void checkSpeciality(Card first, Card second) {
        String name1 = first.getName();
        String name2 = second.getName();

        if (name1.contains("Goblin") && name2.contains("Dragon")) {
            first.setDmg(0);
            battleLogger.log("Goblin too afraid to attack\n");
        } else if (name1.contains("Knight") && name2.contains("WaterSpell")) {
            first.setDmg(0);
            battleLogger.log("Knight drowned\n");
        } else if (name1.contains("FireElve") && name2.contains("Dragon")) {
            second.setDmg(0);
            battleLogger.log("FireElve evaded the attack\n");
        } else if (name1.contains("Kraken") && name2.contains("Spell")) {
            second.setDmg(0);
            battleLogger.log("Kraken is immune\n");
        } else if (name1.contains("Wizzard") && name2.contains("Ork")) {
            second.setDmg(0);
            battleLogger.log("Ork isnt able to attack\n");
        }
    }

    public void battle(String user, String opponent) {
        PrepareCards handleCard = new PrepareCards();
        BattleHandler battleHandler = new BattleHandler();
        StatsHandler statsHandler = new StatsHandler();
        while (Rounds > 0) {
            battleLogger.log("Round: "+ Rounds +"\n");
            Card cardA = handleCard.chooseCard(user);
            Card cardB = handleCard.chooseCard(opponent);
            if (cardA == null || cardB == null) {
                break;
            }
            battleLogger.log(user+" picked"+cardA.getName()+"\n");
            battleLogger.log(opponent+" picked"+cardB.getName()+"\n");
            checkSpeciality(cardA, cardB);
            checkSpeciality(cardB, cardA);
            switch (calculateDMG(cardA, cardB)) {
                case "Won" -> {
                    handleCard.removeCard(cardB, user);
                    statsHandler.win(user);
                    statsHandler.lose(opponent);
                    battleLogger.log(user + " won this round\n");
                    battleLogger.log(opponent + " lost this round\n");
                }
                case "Lost" -> {
                    handleCard.removeCard(cardA, opponent);
                    statsHandler.win(opponent);
                    statsHandler.lose(user);
                    battleLogger.log(opponent + " won this round\n");
                    battleLogger.log(user + " lost this round\n");
                }
                case "Draw" -> battleLogger.log("draw\n");
            }
            --Rounds;
        }
        battleLogger.log("End of battle\n");
        battleHandler.resetBio(user);
        battleHandler.resetBio(opponent);
        try {
            respond.writeHttpResponse(HttpStatus.OK, battleLogger.getLog());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
