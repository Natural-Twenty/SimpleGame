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
        

        assertFalse(dungeon.getlevelComplete());
        player.moveRight();
        assertTrue(dungeon.getlevelComplete());
    }

    @Test
    public void testTwoTreasure() {
        Dungeon dungeon = new Dungeon(3, 1);
        Player player = new Player(dungeon, 0, 0);
        Treasure treasure = new Treasure(1, 0);
        Treasure treasure2 = new Treasure(2, 0);

        dungeon.setPlayer(player);
        dungeon.addEntity(player);
        dungeon.addEntity(treasure);
        treasure.attach(dungeon);
        dungeon.addEntity(treasure2);
        treasure2.attach(dungeon);

        GoalAND getTreasure = new GoalAND();
        getTreasure.addSubGoal(treasure);
        getTreasure.addSubGoal(treasure2);

        dungeon.addGoal(getTreasure);

        assertFalse(dungeon.getlevelComplete());
        player.moveRight();
        assertFalse(dungeon.getlevelComplete());
        player.moveRight();
        assertTrue(dungeon.getlevelComplete());
        
    }
}