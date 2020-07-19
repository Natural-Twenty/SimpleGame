package test;

import org.junit.Test;

import unsw.dungeon.*;

public class PlayerTest {
    //tests the player can move in each direction and that walls block movement

    @Test
    public void test() {
        Dungeon dungeon = new Dungeon(5, 5);

        Player player = new Player(dungeon, 2, 2); // Add player in center
        dungeon.addEntity(player);

        // Wall top = new Wall(2, 0); // Add wall to top of dungeon
        // Wall right = new Wall(4, 2); // Add wall to right of dungeon
        Wall bottom = new Wall(2, 4); // Add wall to bottom of dungeon
        // Wall left = new Wall(0, 2); // Add wall to left of dungeon

        // dungeon.addEntity(top);
        // dungeon.addEntity(right);
        dungeon.addEntity(bottom);
        // dungeon.addEntity(left);

        assert(player.getX() == 2 && player.getY() == 2);

        player.moveDown();
        assert(player.getX() == 2 && player.getY() == 3); // Player moves down one tile

        player.moveLeft();
        assert(player.getX() == 1 && player.getY() == 3); // Player moves left one tile

        player.moveRight();
        assert(player.getX() == 2 && player.getY() == 3); // Player moves right one tile

        player.moveDown();
        assert(player.getX() == 2 && player.getY() == 3); //Player shouldn't be able to enter wall

        player.moveUp();
        assert(player.getX() == 2 && player.getY() == 2);
        

    }
}