package deck;

import Json.ParseJsonImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DeckHandlerImplTest {

    DeckHandlerImpl deckHandler=new DeckHandlerImpl();
    ParseJsonImpl parseJson=new ParseJsonImpl();
    @Test
    void configureDeck() {
        String deck="[\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\"]";
        assertDoesNotThrow(()->assertFalse(deckHandler.configureDeck(parseJson.getJsonnode(deck), "something")));
    }
    @Test
    void notUpdated(){
        String deck2="[\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\",\"aa9999a0-734c-49c6-8f4a-651864b14e62\"]";
        assertDoesNotThrow(()->assertFalse(deckHandler.configureDeck(parseJson.getJsonnode(deck2), "something")));

    }


}