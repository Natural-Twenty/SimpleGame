package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        
        player.moveRight();
        assertTrue(player.getX() == 2 && player.getY() == 1);
        assertTrue(hunter.getX() == 2 && hunter.getY() == 1); //enemy should move into 2, 1 after player triggering players onCollide

        List<Entity> entities = dungeon.getEntities(2, 1);
        assertFalse(entities.contains(player));
    }

    @Test
    public void testPlayerReachesEnemy() {
        Dungeon dungeon = new Dungeon(4, 4);

        Player player = new Player(dungeon, 3, 3);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Hunter hunter = new Hunter(dungeon, 0, 3);
        dungeon.addEntity(hunter);
        player.attach(hunter);

        player.moveUp();
        assertTrue(player.getX() == 3 && player.getY() == 2);
        assertTrue(hunter.getX() == 0 && hunter.getY() == 2);

        player.moveLeft();
        assertTrue(player.getX() == 2 && player.getY() == 2);
        assertTrue(hunter.getX() == 1 && hunter.getY() == 2);

        player.moveLeft(); //player should trigger enemy.onCollide before enemy moves killing player
        assertTrue(player.getX() == 1 && player.getY() == 2);
        //assertTrue(hunter.getX() == 1 && hunter.getY() == 2);

        List<Entity> entities = dungeon.getEntities(1, 2);
        assertFalse(entities.contains(player));
    }

    //Until sword is implemented manually switch value of player.canFight() before runnnig tests

    @Test
    public void testSingleEnemyGoal() {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 0, 0);
        Hunter hunter = new Hunter(dungeon, 4, 0);
        Sword sword = new Sword(0, 1);

        dungeon.addEntity(hunter);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        player.attach(hunter);
        hunter.attach(dungeon);

        dungeon.addGoal(hunter);

        dungeon.addEntity(sword);


        assertTrue(player.getX() == 0 && player.getY() == 0);
        assertTrue(hunter.getX() == 4 && hunter.getY() == 0); //enemy should not move if player has done nothing

        player.moveDown();
        
        assertTrue(player.getX() == 0 && player.getY() == 1);
        assertTrue(hunter.getX() == 4 && hunter.getY() == 1); //enemy should move down to get closer to player

        player.moveRight();
        assertTrue(player.getX() == 1 && player.getY() == 1);
        assertTrue(hunter.getX() == 3 && hunter.getY() == 1); //enemy should move left to get closer to player

        
        player.moveRight();
        assertTrue(player.getX() == 2 && player.getY() == 1);
        assertTrue(hunter.getX() == 2 && hunter.getY() == 1); //enemy should move into 2, 1 after player triggering players onCollide

        List<Entity> entities = dungeon.getEntities(2, 1);
        assertFalse(entities.contains(hunter)); //enemy is killed and removed from dungeon list

        assertEquals(1, dungeon.getlevelComplete()); //dungeon is completed
        player.moveLeft();
        assertFalse(player.getX() == 1 && player.getY() == 1); //goal is complete player shouldnt be able to move
    }

    @Test
    public void testMultipleEnemyGoal() {
        Dungeon dungeon = new Dungeon(4, 4);

        Player player = new Player(dungeon, 0, 0);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Hunter hunter = new Hunter(dungeon, 3, 0);
        Hunter hunter2 = new Hunter(dungeon, 0, 3);

        Sword sword = new Sword(1, 0);
        dungeon.addEntity(sword);

        dungeon.addEntity(hunter);
        dungeon.addEntity(hunter2);

        player.attach(hunter);
        player.attach(hunter2);

        hunter.attach(dungeon);
        hunter2.attach(dungeon);

        GoalAND destroyAllEnemies = new GoalAND();
        destroyAllEnemies.addSubGoal(hunter);
        destroyAllEnemies.addSubGoal(hunter2);

        dungeon.addGoal(destroyAllEnemies);

        player.moveRight();
        assertTrue(player.getX() == 1 && player.getY() == 0);
        assertTrue(hunter.getX() == 2 && hunter.getY() == 0);
        assertTrue(hunter2.getX() == 1 && hunter2.getY() == 3);

        player.moveRight(); //kills hunter
        assertTrue(player.getX() == 2 && player.getY() == 0);
        List<Entity> entities = dungeon.getAllEntities();
        assertFalse(entities.contains(hunter));
        assertTrue(hunter2.getX() == 2 && hunter2.getY() == 3);

        player.moveDown();
        assertTrue(player.getX() == 2 && player.getY() == 1);
        assertTrue(hunter2.getX() == 2 && hunter2.getY() == 2);

        player.moveDown();
        assertTrue(player.getX() == 2 && player.getY() == 2);
        List<Entity> entities2 = dungeon.getAllEntities();
        assertFalse(entities2.contains(hunter2));

        assertEquals(1, dungeon.getlevelComplete()); //dungeon is completed

    }
}