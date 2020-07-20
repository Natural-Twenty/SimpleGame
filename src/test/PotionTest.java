package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

        Potion otherPotion = new Potion(0, 4);
        dungeon.addEntity(otherPotion);

        assertFalse(player.isInvincible());
        player.moveDown(); // Counts as first "move"
        List<Entity> check = dungeon.getEntities(0, 1);
        assertFalse(check.contains(potion)); //potion removed after pickup
        assertTrue(player.isInvincible());

        player.moveDown(); // 2
        player.moveDown(); // 3
        player.moveDown(); // 4
        // Player shouldn't pick up potion
        List<Entity> entities = dungeon.getEntities(0, 4);
        assertTrue(entities.contains(player));
        assertTrue(entities.contains(otherPotion));
        player.moveDown(); // 5
        player.moveDown(); // 6
        player.moveDown(); // 7
        player.moveDown(); // 8
        player.moveDown(); // 9
        player.moveDown(); // 10
        assertTrue(player.isInvincible());
        player.moveDown(); // During 11th turn player is invincible but when update runs at end it is removed
        assertFalse(player.isInvincible());
        //So you stay invincible for 10 moves
    }

    @Test
    public void testEnemyResponse() {
        Dungeon dungeon = new Dungeon(10, 7);

        Player player = new Player(dungeon, 6, 3);
        Hunter hunterBelow = new Hunter(dungeon, 6, 5);
        Hunter hunterAbove = new Hunter(dungeon, 6, 2);
        Hunter hunterLeft = new Hunter(dungeon, 5, 3);
        Hunter hunterRight = new Hunter(dungeon, 7, 3);
        Potion potion = new Potion(6, 4);

        dungeon.setPlayer(player);

        dungeon.addEntity(player);
        dungeon.addEntity(potion);
        dungeon.addEntity(hunterBelow);
        dungeon.addEntity(hunterAbove);
        dungeon.addEntity(hunterLeft);
        dungeon.addEntity(hunterRight);
        
        player.attach(hunterBelow);
        player.attach(hunterAbove);
        player.attach(hunterLeft);
        player.attach(hunterRight);
        

        assertFalse(player.isInvincible());
        player.moveDown(); // Counts as first "move"
        assertTrue(player.isInvincible());

        //Player moves and becomes invincible before updating all its Observers at end of turn
        //So enemies should be doing reverse behaviour to normal
        assertTrue(hunterBelow.getX() == 6 && hunterBelow.getY() == 6); //should move in reverse to normal behaviour
        assertTrue(hunterAbove.getX() == 6 && hunterAbove.getY() == 1);
        assertTrue(hunterLeft.getX() == 4 && hunterLeft.getY() == 3);
        assertTrue(hunterRight.getX() == 8 && hunterRight.getY() == 3);
        
        player.moveUp(); // 2

        assertTrue(hunterBelow.getX() == 6 && hunterBelow.getY() == 6); //should move in reverse to normal behaviour
        assertTrue(hunterAbove.getX() == 6 && hunterAbove.getY() == 0);
        assertTrue(hunterLeft.getX() == 3 && hunterLeft.getY() == 3);
        assertTrue(hunterRight.getX() == 9 && hunterRight.getY() == 3);
        
    }
}