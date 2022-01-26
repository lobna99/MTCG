package stats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsHandlerImplTest {

    StatsHandlerImpl statsHandler=new StatsHandlerImpl();

    @Test
    void calculateElo() {
        int elo1=100;
        int elo2=100;
        elo1= statsHandler.calculateElo(elo1,elo2,1);
        assertTrue(elo1>elo2);
        elo2= statsHandler.calculateElo(elo2,elo1,1);
        elo2= statsHandler.calculateElo(elo2,elo1,1);
        assertTrue(elo2>elo1);
    }
    @Test
    void calculateEloLose(){
        int elo1=100;
        int elo2=100;
        elo1= statsHandler.calculateElo(elo1,elo2,0);
        assertFalse(elo1>elo2);
        elo2= statsHandler.calculateElo(elo2,elo1,0);
        elo2= statsHandler.calculateElo(elo2,elo1,0);
        assertFalse(elo2>elo1);
    }

    @Test
    void calculateEloDraw(){
        int elo1=100;
        int elo2=100;
        elo1= statsHandler.calculateElo(elo1,elo2,0.5);
        elo2= statsHandler.calculateElo(elo2,elo1,0.5);
        assertEquals(elo1,elo2);
    }
}