package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import unsw.dungeon.*;

public class PickaxeTest {
    @Test
    public void testPickaxe() {
        Dungeon dungeon = new Dungeon(5, 5);

        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Pickaxe pickaxe = new Pickaxe(2, 1);
        dungeon.addEntity(pickaxe);

        Wall wall = new Wall(3, 1);
        dungeon.addEntity(wall);

        player.moveRight();
        assertTrue(player.hasPickaxe());
        assertFalse(dungeon.getAllEntities().contains(pickaxe));

        player.moveRight();
        assertTrue(player.getX() == 3 && player.getY() == 1);
    }
}