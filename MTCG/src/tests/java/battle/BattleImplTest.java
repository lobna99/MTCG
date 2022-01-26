package battle;

import cards.Card;
import cards.Elements;
import cards.Monstercard;
import cards.Spellcard;
import org.junit.jupiter.api.Test;
import user.User;

import static org.junit.jupiter.api.Assertions.*;

class BattleImplTest {

    Card cardA=new Monstercard("845f0dc7-37d0-426e-994e-43fc3ac83c08","WaterGoblin", Elements.Water,20);
    Card cardB=new Monstercard("845f0dc7-37d0-426e-994e-4324234345383c08","FireDragon", Elements.Fire,30);
    Card cardC=new Spellcard("845f0dc7-37d0-426e-994e-43242343sadfs342","RegularSpell", Elements.Regular,10);
    BattleImpl battle=new BattleImpl();

    @Test
    void calculateDMG() {
    assertEquals("Won",battle.calculateDMG(cardB,cardA,new User("Lol"," "),new User("O"," ")));
    assertEquals("Lost",battle.calculateDMG(cardA,cardB,new User("Lol"," "),new User("O"," ")));
    }
    @Test
    void checkSpeciality() {
        battle.checkSpeciality(cardA,cardB);
        assertEquals(0,cardA.getDmg());
    }
    @Test
    void noSpeciality() {
        battle.checkSpeciality(cardA,cardC);
        assertEquals(20,cardA.getDmg());
    }
    @Test
    void calculateElementDMG() {
        assertEquals("Won",battle.calculateDMG(cardC,cardA,new User("Lol"," "),new User("O"," ")));
        assertEquals("Lost",battle.calculateDMG(cardC,cardB,new User("Lol"," "),new User("O"," ")));
    }

}