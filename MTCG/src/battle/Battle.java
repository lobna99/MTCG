package battle;

import cards.Card;
import Http.HttpStatus;
import response.Response;
import response.Responsebuilder;
import stats.StatsHandler;

import java.io.IOException;
import java.util.ArrayList;

public class Battle implements BattleInterface, Response {
    private static Battle OBJ = null;


    public static Battle getInstance() {
        if (OBJ == null)
            OBJ = new Battle();

        return OBJ;
    }

    private Battle() {
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
        ArrayList<Card> userCards = handleCard.chooseCard(user);
        ArrayList<Card> oppCards = handleCard.chooseCard(opponent);
        int userwon = 0;
        int userlost = 0;
        int oppwon = 0;
        int opplost = 0;
        int elo1=statsHandler.getELO(user);
        int elo2=statsHandler.getELO(opponent);
        int rounds;
        for (rounds = 100; rounds > 0; rounds--) {
            battleLogger.log("Round: " + rounds + "\n");
            Card cardA = userCards.get((int) (Math.random() * userCards.size()));
            Card cardB = oppCards.get((int) (Math.random() * oppCards.size()));
            battleLogger.log(user + " picked : " + cardA.getName() + "\n");
            battleLogger.log(opponent + " picked : " + cardB.getName() + "\n");
            checkSpeciality(cardA, cardB);
            checkSpeciality(cardB, cardA);
            switch (calculateDMG(cardA, cardB)) {
                case "Won" -> {
                    userCards.add(cardB);
                    oppCards.remove(cardB);
                    elo1=statsHandler.calculateElo(elo1,elo2,1);
                    elo2=statsHandler.calculateElo(elo2,elo1,0);
                    userwon++;
                    opplost++;
                    battleLogger.log(user + " won this round\n");
                    battleLogger.log(opponent + " lost this round\n");
                }
                case "Lost" -> {
                    oppCards.add(cardA);
                    userCards.remove(cardA);
                    elo1=statsHandler.calculateElo(elo1,elo2,0);
                    elo2=statsHandler.calculateElo(elo2,elo1,1);
                    oppwon++;
                    userlost++;
                    battleLogger.log(opponent + " won this round\n");
                    battleLogger.log(user + " lost this round\n");
                }
                case "Draw" -> {
                    battleLogger.log("draw\n");
                    elo1=statsHandler.calculateElo(elo1,elo2,0.5);
                    elo2=statsHandler.calculateElo(elo2,elo1,0.5);

                }
            }
            if (oppCards.size() ==0 || userCards.size() == 0) {
                break;
            }
        }
        battleLogger.log("End of battle\n");
        statsHandler.updateStats(user, userlost,userwon,elo1);
        statsHandler.updateStats(opponent, opplost,oppwon,elo2);
        battleHandler.resetBio(user);
        battleHandler.resetBio(opponent);
        try {
            respond.writeHttpResponse(HttpStatus.OK, battleLogger.getLog());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
