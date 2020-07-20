package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Test;

import unsw.dungeon.*;

public class EnemyTest {
    
    @Test
    public void testEnemyReachesPlayer() {
        Dungeon dungeon = new Dungeon(5, 5);

        Player player = new Player(dungeon, 0, 0);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Hunter hunter = new Hunter(dungeon, 4, 0);
        dungeon.addEntity(hunter);
        player.attach(hunter);

        assertTrue(player.getX() == 0 && player.getY() == 0);
        assertTrue(hunter.getX() == 4 && hunter.getY() == 0); //enemy should not move if player has done nothing

        player.moveDown();
        
        assertTrue(player.getX() == 0 && player.getY() == 1);
        assertTrue(hunter.getX() == 4 && hunter.getY() == 1); //enemy should move down to get closer to player

        player.moveRight();
        assertTrue(player.getX() == 1 && player.getY() == 1);
        assertTrue(hunter.getX() == 3 && hunter.getY() == 1); //enemy should move left to get closer to player

        //test not complete need to check edge cases of interacting
        player.moveRight();
        assertTrue(player.getX() == 2 && player.getY() == 1);
        assertTrue(hunter.getX() == 2 && hunter.getY() == 1); //enemy should move into 2, 1 after player triggering players onCollide

        List<Entity> entities = dungeon.getEntities(2, 1);

        assertFalse(entities.contains(player));
    }

    @Test
    public void testPlayerReachesEnemy() {

    }
}