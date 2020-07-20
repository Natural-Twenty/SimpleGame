package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import unsw.dungeon.*;

public class PlayerTest {
    //tests the player can move in each direction and that walls block movement

    @Test
    public void test() {
        Dungeon dungeon = new Dungeon(5, 5);

        Player player = new Player(dungeon, 2, 2); // Add player in center
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        
        Wall bottom = new Wall(2, 4); // Add wall to bottom of dungeon
        dungeon.addEntity(bottom);
        

        assert(player.getX() == 2 && player.getY() == 2);

        player.moveDown();
        assertTrue(player.getX() == 2 && player.getY() == 3); // Player moves down one tile

        player.moveLeft();
        assertTrue(player.getX() == 1 && player.getY() == 3); // Player moves left one tile

        player.moveRight();
        assertTrue(player.getX() == 2 && player.getY() == 3); // Player moves right one tile

        player.moveDown();
        assertTrue(player.getX() == 2 && player.getY() == 3); //Player shouldn't be able to enter wall

        player.moveUp();
        assertTrue(player.getX() == 2 && player.getY() == 2);
        

    }
}