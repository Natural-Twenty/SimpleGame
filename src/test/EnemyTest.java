package test;

import org.junit.Test;

import unsw.dungeon.*;

public class EnemyTest {
    
    @Test
    public void test() {
        Dungeon dungeon = new Dungeon(5, 5);

        Player player = new Player(dungeon, 0, 0);
        dungeon.addEntity(player);

        Hunter hunter = new Hunter(dungeon, 4, 0);
        dungeon.addEntity(hunter);

        player.attach(hunter);

        assert(player.getX() == 0 && player.getY() == 0);
        assert(hunter.getX() == 4 && hunter.getY() == 0); //enemy should not move if player has done nothing

        player.moveDown();
        
        assert(player.getX() == 0 && player.getY() == 1);
        assert(hunter.getX() == 4 && hunter.getY() == 1); //enemy should move down to get closer to player

        player.moveRight();
        assert(player.getX() == 1 && player.getY() == 1);
        assert(hunter.getX() == 3 && hunter.getY() == 1); //enemy should move left to get closer to player

        //test not complete need to check edge cases of interacting

    }
}