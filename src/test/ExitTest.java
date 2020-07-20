package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import unsw.dungeon.*;

public class ExitTest {

    @Test
    public void test() {
        Dungeon dungeon = new Dungeon(5, 5);

        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        Exit exit = new Exit(1, 2);
        dungeon.addEntity(exit);
        dungeon.addGoal(exit);

        exit.attach(dungeon); //set dungeon to observe exit

        //move player onto exit
        player.moveDown();
        assertTrue(player.getX() == 1 && player.getY() == 2);

        //player shouldn't move since they triggered all goals
        player.moveDown();
        assertTrue(player.getX() == 1 && player.getY() == 2);

    }
}