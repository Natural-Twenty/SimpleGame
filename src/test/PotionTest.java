package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import unsw.dungeon.*;

public class PotionTest {
    @Test
    public void testPotionLength() {
        Dungeon dungeon = new Dungeon(1, 15);

        Player player = new Player(dungeon, 0, 0);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Potion potion = new Potion(0, 1);
        dungeon.addEntity(potion);

        assertFalse(player.isInvincible());
        player.moveDown(); // Counts as first "move"
        assertTrue(player.isInvincible());

        player.moveDown(); // 2
        player.moveDown(); // 3
        player.moveDown(); // 4
        player.moveDown(); // 5
        player.moveDown(); // 5
        player.moveDown(); // 6
        player.moveDown(); // 7
        player.moveDown(); // 8
        player.moveDown(); // 9
        assertTrue(player.isInvincible());
        player.moveDown(); // During 10th turn player is invincible but when update runs at end it is removed
        assertFalse(player.isInvincible());
    }

    @Test
    public void testEnemyResponse() {
        Dungeon dungeon = new Dungeon(15, 15);

        Player player = new Player(dungeon, 7, 7);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Potion potion = new Potion(7, 8);
        dungeon.addEntity(potion);

        Hunter hunterBelow = new Hunter(dungeon, 7, 9);
        Hunter hunterAbove = new Hunter(dungeon, 7, 6);
        Hunter hunterLeft = new Hunter(dungeon, 6, 7);
        Hunter hunterRight = new Hunter(dungeon, 8, 7);

        dungeon.addEntity(hunterLeft);
        player.attach(hunterLeft);

        dungeon.addEntity(hunterRight);
        player.attach(hunterRight);

        dungeon.addEntity(hunterBelow);
        player.attach(hunterBelow);

        dungeon.addEntity(hunterAbove);
        player.attach(hunterAbove);

        assertFalse(player.isInvincible());
        player.moveDown(); // Counts as first "move"
        assertTrue(player.isInvincible());

        assertTrue(hunterBelow.getX() == 7 && hunterBelow.getY() == 10); //should move in reverse to normal behaviour
        assertTrue(hunterAbove.getX() == 7 && hunterAbove.getY() == 5);
        assertTrue(hunterLeft.getX() == 5 && hunterLeft.getY() == 7);
        assertTrue(hunterRight.getX() == 9 && hunterRight.getY() == 7);
        
        player.moveDown(); // 2
        player.moveDown(); // 3
        player.moveDown(); // 4
        player.moveDown(); // 5
        player.moveDown(); // 5
        player.moveDown(); // 6
        player.moveDown(); // 7
        player.moveDown(); // 8
        player.moveDown(); // 9
        assertTrue(player.isInvincible());
        player.moveDown(); // During 10th turn player is invincible but when update runs at end it is removed
        assertFalse(player.isInvincible());
    }
}