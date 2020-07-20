package test;

import org.junit.Test;

import unsw.dungeon.*;

// Test portal functionality
public class testPortal {
    
    @Test
    public void testPlayer () {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 1);
        Portal portal1 = new Portal(dungeon, 2, 1, 1);
        Portal portal2 = new Portal(dungeon, 4, 4, 1);

        dungeon.addEntity(player);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        // Move player into portal1
        player.moveTo(2, 1);
        assert(player.getX() == 4 && player.getY() == 4);
    }
}