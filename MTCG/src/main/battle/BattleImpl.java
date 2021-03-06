package battle;

import cards.Card;
import cards.Spellcard;
import response.Response;
import user.User;

import java.io.IOException;
import java.sql.SQLException;

public class BattleImpl implements Battle, Response {

    public BattleImpl() {
    }

    public String calculateDMG(Card first, Card second, User Player1, User Player2) {

        double dmg1 = first.getDmg();
        double dmg2 = second.getDmg();
        int elem1 = first.getElement().getElem();
        int elem2 = second.getElement().getElem();

        //if one card is spell card then element does matter
        if (first.getClass().equals(Spellcard.class) || second.getClass().equals(Spellcard.class)) {
            if (elem1 - elem2 < 0 || elem1 - elem2 == 2) {
                dmg1 *= 2;
                dmg2 = dmg2 / 2;
            } else if (elem1 - elem2 > 0 || elem1 - elem2 == -2) {
                dmg1 = dmg1 / 2;
                dmg2 *= 2;
            }
        }
        //check if someone got a streak to increase their damage
        if (Player1.getStreak() > 0) {
            dmg1 += Player1.getStreak() * 1.25;
        } else if (Player2.getStreak() > 0) {
            dmg2 += Player2.getStreak() * 1.25;
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

    //check if speciality is activated
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

    public String battle(String user, String opponent) throws SQLException, IOException {
        User Player1 = new User(user, "");
        User Player2 = new User(opponent, "");
        Player1.setCards(handleCard.chooseCard(Player1.getUsername()));//get Deck and set to array
        Player2.setCards(handleCard.chooseCard(Player2.getUsername()));
        Player1.setELO(statsHandler.getELO(Player1.getUsername()));
        Player2.setELO(statsHandler.getELO(Player2.getUsername()));

        for (int rounds = 1; rounds <= 100; rounds++) {
            battleLogger.log("Round: " + rounds + "\n");
            Card cardA = Player1.getCards().get((int) (Math.random() * Player1.getCards().size()));//select random card from deck
            Card cardB = Player2.getCards().get((int) (Math.random() * Player2.getCards().size()));
            battleLogger.log(Player1.getUsername() + " picked : " + cardA.getName() + "\n");
            battleLogger.log(Player2.getUsername() + " picked : " + cardB.getName() + "\n");
            checkSpeciality(cardA, cardB);
            checkSpeciality(cardB, cardA);
            switch (calculateDMG(cardA, cardB, Player1, Player2)) {//check if player1 won or lost or draw
                case "Won" -> {
                    playerWon(Player1, Player2, cardB);
                }
                case "Lost" -> {
                    playerWon(Player2, Player1, cardA);
                }
                case "Draw" -> {
                    battleLogger.log("draw\n");
                    Player1.setELO(statsHandler.calculateElo(Player1.getELO(), Player2.getELO(), 0.5));
                    Player2.setELO(statsHandler.calculateElo(Player2.getELO(), Player1.getELO(), 0.5));
                }
            }
            if (Player1.getCards().isEmpty()){//check if one of players has no cards left to break loop
                battleLogger.log(Player1.getUsername()+" has no Cards left\n");
                break;
            }else if(Player2.getCards().isEmpty()) {
                battleLogger.log(Player2.getUsername()+" has no Cards left\n");
                break;
            }
        }
        battleLogger.log("End of battle\n");
        double ratio1=Player1.getWon()/(double)(Player1.getWon()+Player1.getLost());//calculate win ratio of player
        double ratio2=Player2.getWon()/(double)(Player2.getWon()+Player2.getLost());
        statsHandler.updateStats(Player1.getUsername(), Player1.getLost(), Player1.getWon(), Player1.getELO(), ratio1);
        statsHandler.updateStats(Player2.getUsername(), Player2.getLost(), Player2.getWon(), Player2.getELO(), ratio2);
        battleHandler.resetBio(Player1.getUsername());
        battleHandler.resetBio(Player2.getUsername());
        return battleLogger.getLog();
    }

    public void playerWon(User Player1, User Player2, Card card) {
        Player1.getCards().add(card);//add loser card to winers deck
        Player2.getCards().remove(card);//remove card of loser from their deck
        Player1.setELO(statsHandler.calculateElo(Player1.getELO(), Player2.getELO(), 1));//update elo
        Player2.setELO(statsHandler.calculateElo(Player2.getELO(), Player1.getELO(), 0));
        Player1.setWon(Player1.getWon() + 1);//update number of won
        Player2.setLost(Player2.getLost() + 1);
        battleLogger.log(Player1.getUsername() + " won this round\n");
        battleLogger.log(Player2.getUsername() + " lost this round\n");
        Player1.setStreak(Player1.getStreak() + 1);//set straek for winner
        Player2.setStreak(0);
    }
}
