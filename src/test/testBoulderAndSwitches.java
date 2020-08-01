package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import unsw.dungeon.*;
// Test that the boulder moves as intended and correctly triggers and untriggers floor switches
public class testBoulderAndSwitches {
    
    @Test
    // Test Boulder movement without barriers such as walls
    public void testMovement() {
        Dungeon dungeon = new Dungeon(5, 5);
        Boulder boulder = new Boulder(dungeon, 2, 2);
        Player player = new Player(dungeon, 2, 1);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(boulder);
        // push boulder down
        player.moveTo(2, 2);
        assert(boulder.getX() == 2 && boulder.getY() == 3);
        assert(player.getX() == 2 && player.getY() == 2);
        // push boulder right
        player.moveTo(1, 3);
        player.moveTo(2, 3);
        assert(boulder.getX() == 3 && boulder.getY() == 3);
        assert(player.getX() == 2 && player.getY() == 3);
        // push boulder left
        player.moveTo(4,3);
        player.moveTo(3, 3);
        assert(boulder.getX() == 2 && boulder.getY() == 3);
        assert(player.getX() == 3 && player.getY() == 3);
        // push boulder up
        player.moveTo(2, 4);
        player.moveTo(2,3);
        assert(boulder.getX() == 2 && boulder.getY() == 2);
        assert(player.getX() == 2 && player.getY() == 3);
    }

    @Test
    // Test boulder movement against barriers such as walls and other boulders
    public void testBarriers() {
        // Spawn a boulder and another boulder to the right of it.
        Dungeon dungeon = new Dungeon(5, 5);
        Boulder boulderMove = new Boulder(dungeon, 2, 2);
        Boulder boulderWall = new Boulder(dungeon, 3, 2);
        Player player = new Player(dungeon, 1, 2);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(boulderMove);
        dungeon.addEntity(boulderWall);
        //dungeon.addEntity(player);
        // Spawn a wall to the top of the first boulder.
        Wall wall = new Wall(2, 1);
        dungeon.addEntity(wall);
        // Push boulder right (test collision against another boulder)
        player.moveTo(2, 2);
        assert(boulderMove.getX() == 2 && boulderMove.getY() == 2);
        assert(player.getX() == 1 && player.getY() == 2);
        // Push boulder up (test collision against wall)
        player.moveTo(2, 3);
        player.moveTo(2, 2);
        assert(boulderMove.getX() == 2 && boulderMove.getY() == 2);
        assert(player.getX() == 2 && player.getY() == 3);
    }

    @Test
    // Test boulder interacting with floor switches
    public void testFloorSwitch() {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 2);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Boulder boulder = new Boulder(dungeon, 2, 2);
        dungeon.addEntity(boulder);

        FloorSwitch floorSwitch = new FloorSwitch(dungeon, 2, 2);
        dungeon.addEntity(floorSwitch);

        // Boulder and floor switch occupy the same coordinates
        assert(floorSwitch.isTriggered());
        //Move boulder right
        player.moveTo(2, 2);
        assert(!floorSwitch.isTriggered());
        //Move boulder left (back to original position)
        player.moveTo(4, 2);
        player.moveTo(3, 2);
        assert(floorSwitch.isTriggered());
        
    }

    @Test
    public void testBoulderGoal() {
        //Create all entities
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 0, 0);

        Boulder boulder1 = new Boulder(dungeon, 0, 1);
        FloorSwitch floorSwitch1 = new FloorSwitch(dungeon, 0, 2);

        Boulder boulder2 = new Boulder(dungeon, 1, 0);
        FloorSwitch floorSwitch2 = new FloorSwitch(dungeon, 2, 0);

        //Add all entities to the dungeon
        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        
        dungeon.addEntity(boulder1);
        dungeon.addEntity(floorSwitch1);
        dungeon.addEntity(boulder2);
        dungeon.addEntity(floorSwitch2);

        //Attach observers to our leaf goals
        floorSwitch1.attach(dungeon);
        floorSwitch2.attach(dungeon);

        //Combine our leaf goals under a AND goal
        GoalAND moveAllBoulders = new GoalAND();
        moveAllBoulders.addSubGoal(floorSwitch1);
        moveAllBoulders.addSubGoal(floorSwitch2);

        //Set dungeon goal to this goal
        dungeon.addGoal(moveAllBoulders);

        //move boulder onto switch2
        assertFalse(floorSwitch2.isTriggered());
        player.moveRight(); //should push boulder 2 onto plate 2
        assertTrue(floorSwitch2.isTriggered());

        player.moveLeft();

        //move boulder onto switch1
        assertFalse(floorSwitch1.isTriggered());
        player.moveDown(); //should push boulder 1 onto plate 1 completing all goals freezing player
        assertTrue(floorSwitch1.isTriggered());

        assertEquals(1, dungeon.getlevelComplete());

        player.moveUp(); //player shouldn't move back to 0,0
        assertFalse(player.getX() == 0 && player.getY() == 0);


    }
}