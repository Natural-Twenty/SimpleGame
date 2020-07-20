package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import unsw.dungeon.*;

public class TreasureTest {
    
    @Test
    public void testOneTreasure() {
        Dungeon dungeon = new Dungeon(2, 1);
        Player player = new Player(dungeon, 0, 0);
        Treasure treasure = new Treasure(1, 0);

        dungeon.setPlayer(player);
        dungeon.addEntity(player);
        dungeon.addEntity(treasure);
        treasure.attach(dungeon);
        dungeon.addGoal(treasure);
        

        assertFalse(dungeon.getCompletion());
        player.moveRight();
        assertTrue(dungeon.getCompletion());
    }
}