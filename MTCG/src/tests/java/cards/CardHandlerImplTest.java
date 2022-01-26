package cards;

import Json.ParseJsonImpl;
import org.junit.jupiter.api.Test;
import packages.Package;
import packages.PackageHandlerImpl;

import static org.junit.jupiter.api.Assertions.*;

class CardHandlerImplTest {

    CardHandlerImpl cardHandler=new CardHandlerImpl();
    Package pack=new Package();
    ParseJsonImpl parseJson=new ParseJsonImpl();
    Card cardA=new Monstercard("845f0dc7-37d0-426e-994e-43fc3ac83c08","WaterGoblin", Elements.Water,20);
    Card cardB=new Monstercard("845f0dc7-37d0-426e-994e-4324234345383c08","FireDragon", Elements.Fire,30);
    Card cardC=new Monstercard("845f0dc7-37d0-426e-994e-43242343sadfs342","RegularSpell", Elements.Regular,10);
    String cards="[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]";
    String cardsnotenough="[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]";

    @Test
    void extractnewcards() {
    pack.setId(1);
    assertDoesNotThrow(()->assertTrue(cardHandler.extractnewcards(parseJson.getJsonnode(cards), pack)));
    }
    @Test
    void extractnewcardsFAIL() {
        pack.setId(1);
        assertDoesNotThrow (()->assertFalse(cardHandler.extractnewcards(parseJson.getJsonnode(cardsnotenough), pack)));
    }
    @Test
    void testSetter(){
        cardA.setDmg(100);
        cardB.setDmg(200);
        cardC.setDmg(300);
        assertEquals(100,cardA.getDmg());
        assertEquals(200,cardB.getDmg());
        assertEquals(300,cardC.getDmg());
    }
}