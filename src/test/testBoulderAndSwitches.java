package test;


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
        dungeon.addEntity(boulderMove);
        dungeon.addEntity(boulderWall);
        dungeon.addEntity(player);
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
    // Test that enemies cannot move boulders
    public void testBoulderWithEnemies() {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 2);
        Hunter hunter = new Hunter(dungeon, 3, 2);
        Boulder boulder1 = new Boulder(dungeon, 2, 2);
        Boulder boulder2 = new Boulder(dungeon, 3, 1);
        Boulder boulder3 = new Boulder(dungeon, 4, 2);
        Boulder boulder4 = new Boulder(dungeon, 3, 3);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(hunter);
        player.attach(hunter);
        dungeon.addEntity(boulder1);
        dungeon.addEntity(boulder2);
        dungeon.addEntity(boulder3);
        dungeon.addEntity(boulder4);
        // Move player, enemy should not move since it cannot push boulders
        player.moveTo(0, 2);
        assert(hunter.getX() == 3 && hunter.getY() == 2);
        assert(boulder1.getX() == 2 && boulder1.getY() == 2);
        assert(boulder2.getX() == 3 && boulder2.getY() == 1);
        assert(boulder3.getX() == 4 && boulder3.getY() == 2);
        assert(boulder4.getX() == 3 && boulder4.getY() == 3);
        player.moveTo(1, 2);
        assert(hunter.getX() == 3 && hunter.getY() == 2);
        assert(boulder1.getX() == 2 && boulder1.getY() == 2);
        assert(boulder2.getX() == 3 && boulder2.getY() == 1);
        assert(boulder3.getX() == 4 && boulder3.getY() == 2);
        assert(boulder4.getX() == 3 && boulder4.getY() == 3);
    }
    
}