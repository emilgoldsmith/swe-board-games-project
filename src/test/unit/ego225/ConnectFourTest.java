package test.unit.ego225;

import api.Game;
import impl.game.ConnectFour;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnectFourTest {
    @Test
    public void testDefaultConstructor() {
        Game game = new ConnectFour();
        assertEquals("has 6 rows", 6, game.getRows());
        assertEquals("has 7 columns", 7, game.getColumns());
    }
}
