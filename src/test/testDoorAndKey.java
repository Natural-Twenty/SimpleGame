package test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import unsw.dungeon.*;

public class testDoorAndKey {
    @Test
    // Test door is a barrier if player does not have key
    public void testDoorNoKey() {
        Dungeon dungeon = new Dungeon(5, 5);
        Door door = new Door(3, 1, 1);
        Player player = new Player(dungeon, 2, 1);

        dungeon.addEntity(door);
        dungeon.addEntity(player);
        
        player.moveTo(3, 1);
        assert(player.getX() == 2 && player.getY() == 1);
    }

    @Test
    // Test Door with matching ID key and key is removed from dungeon's entity list
    public void testDoorWithRightKey() {
        Dungeon dungeon = new Dungeon(5, 5);
        Door door = new Door(3, 1, 1);
        Player player = new Player(dungeon, 1, 1);
        Key key = new Key(2, 1, 1);

        dungeon.addEntity(door);
        dungeon.addEntity(player);
        dungeon.addEntity(key);
        // Pickup key
        player.moveTo(2, 1);
        assert(player.getInventory().contains(key));
        assertFalse(dungeon.getAllEntities().contains(key));
        // Open door
        player.moveTo(3, 1);
        assert(player.getX() == 3 && player.getY() == 1);
        assertFalse(player.getInventory().contains(key));
    }

    @Test
    // Test Door with non-matching ID key.
    public void testDoorWithWrongKey() {
        Dungeon dungeon = new Dungeon(5, 5);
        Door door = new Door(3, 1, 1);
        Player player = new Player(dungeon, 1, 1);
        Key key = new Key(2, 1, 2);

        dungeon.addEntity(door);
        dungeon.addEntity(player);
        dungeon.addEntity(key);
        // Pickup key
        player.moveTo(2, 1);
        assert(player.getInventory().contains(key));
        assertFalse(dungeon.getAllEntities().contains(key));
        // Try open door, key should not be used.
        player.moveTo(3, 1);
        assert(player.getX() == 2 && player.getY() == 1);
        assert(player.getInventory().contains(key));
    }

    @Test
    // Test only one key in inventory at a time and test scenario where
    // there are multiple keys and doors that open with correct key as intented.
    public void testDoorMultipleKeys() {
        Dungeon dungeon = new Dungeon(5, 5);
        Door door1 = new Door(3, 1, 1);
        Door door2 = new Door(3, 2, 2);
        Player player = new Player(dungeon, 1, 1);
        Key key1 = new Key(2, 1, 1);
        Key key2 = new Key(2, 2, 2);

        dungeon.addEntity(door1);
        dungeon.addEntity(door2);
        dungeon.addEntity(player);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        // move to key 1
        player.moveTo(2, 1);
        assertFalse(dungeon.getAllEntities().contains(key1));
        assert(player.getInventory().contains(key1));
        // try to open door 2
        player.moveTo(3, 2);
        assert(player.getX() == 2 && player.getY() == 1);
        assert(player.getInventory().contains(key1));
        // try to pick up key 2
        player.moveTo(2, 2);
        assertFalse(player.getInventory().contains(key2));
        assert(dungeon.getAllEntities().contains(key2));
        // Open door 1
        player.moveTo(3, 1);
        assert(player.getX() == 3 && player.getY() == 1);
        assertFalse(player.getInventory().contains(key1));
        // Pickup key 2
        player.moveTo(2, 2);
        assertFalse(dungeon.getAllEntities().contains(key2));
        assert(player.getInventory().contains(key2));
        // Move to door 1 - check an open door doesn't intefere with key
        player.moveTo(3, 1);
        assert(player.getX() == 3 && player.getY() == 1);
        assert(player.getInventory().contains(key2));
        // Try open door 2
        player.moveTo(3, 2);
        assert(player.getX() == 3 && player.getY() == 2);
        assertFalse(player.getInventory().contains(key2));
    }

    @Test
    public void testDoorWithEnemies() {
        Dungeon dungeon = new Dungeon(5, 5);
        Door door = new Door(3, 1, 1);
        Player player = new Player(dungeon, 1, 1);
        Key key = new Key(2, 1, 1);
        Hunter hunter = new Hunter(dungeon, 4, 1);
        Wall wall1 = new Wall(4, 0);
        Wall wall2 = new Wall(5, 1);
        Wall wall3 = new Wall(4, 2);

        dungeon.addEntity(door);
        dungeon.addEntity(player);
        dungeon.addEntity(key);
        dungeon.addEntity(hunter);
        player.attach(hunter);
        dungeon.addEntity(wall1);
        dungeon.addEntity(wall2);
        dungeon.addEntity(wall3);
        // Move to key
        player.moveTo(2, 1);
        assert(hunter.getX() == 4 && hunter.getY() == 1);
        assert(player.getInventory().contains(key));
        assertFalse(dungeon.getAllEntities().contains(key));
        // Open door
        player.moveTo(3, 1);
        assert(hunter.getX() == 3 && hunter.getY() == 1);
        assertFalse(player.getInventory().contains(key));



    }
}