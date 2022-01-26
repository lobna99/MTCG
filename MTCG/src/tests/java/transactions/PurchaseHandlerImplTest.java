package transactions;

import Json.ParseJsonImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseHandlerImplTest {

    PurchaseHandlerImpl purchaseHandler=new PurchaseHandlerImpl();

    @Test
    void pay() {
    assertDoesNotThrow(()->assertFalse(purchaseHandler.pay("hmmmm",1)));
    }

    @Test
    void purchasePack() {
       assertDoesNotThrow(()->assertFalse(purchaseHandler.purchasePack("blabla")));
    }
}