package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import unsw.dungeon.*;

public class SwordTest {
    @Test
    public void swordBreaks() {
        Dungeon dungeon = new Dungeon(1, 6);
        Player player = new Player(dungeon, 0, 0);
        Hunter hunter1 = new Hunter(dungeon, 0, 5);
        Hunter hunter2 = new Hunter(dungeon, 0, 5);
        Hunter hunter3 = new Hunter(dungeon, 0, 5);
        Hunter hunter4 = new Hunter(dungeon, 0, 5);
        Hunter hunter5 = new Hunter(dungeon, 0, 5);

        Sword sword = new Sword(0, 1);
        Sword sword2 = new Sword(0, 2);

        dungeon.setPlayer(player);
        dungeon.addEntity(hunter1);
        dungeon.addEntity(hunter2);
        dungeon.addEntity(hunter3);
        dungeon.addEntity(hunter4);
        dungeon.addEntity(hunter5);
        dungeon.addEntity(sword);
        dungeon.addEntity(sword2);

        player.attach(hunter1);
        player.attach(hunter2);
        player.attach(hunter3);
        player.attach(hunter4);
        player.attach(hunter5);

        player.moveDown();
        assertTrue(player.hasSword());

        //shouldnt pick up existing sword
        player.moveDown();
        assertTrue(dungeon.getEntities(0, 2).contains(sword2));

        player.moveDown();
        assertFalse(player.hasSword());
    }
}