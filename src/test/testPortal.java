package test;

import org.junit.Test;

import unsw.dungeon.*;

// Test portal functionality
public class testPortal {
    
    @Test
    public void testPlayer () {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 1);
        Portal portal1 = new Portal(dungeon, 2, 1, 1);
        Portal portal2 = new Portal(dungeon, 4, 4, 1);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        dungeon.addEntity(player);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        // Move player into portal1
        player.moveTo(2, 1);
        assert(player.getX() == 4 && player.getY() == 4);
    }

    @Test
    public void testHunter() {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 1);
        Portal portal1 = new Portal(dungeon, 2, 1, 1);
        Portal portal2 = new Portal(dungeon, 4, 1, 1);
        Hunter hunter = new Hunter(dungeon, 5, 1);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);

        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        dungeon.addEntity(hunter);
        player.attach(hunter);
        //Move player left
        player.moveTo(0, 1);
        assert(hunter.getX() == 2 && hunter.getY() == 1);
        assert(player.getX() == 0 && player.getY() == 1);
    }

    @Test
    // Portals with something blocking the otherside should not teleport entities
    // This tests that players cannot enter a portal when it's paired portal is 
    // obstucted.
    public void testPortalBlocking1() {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 1);
        Portal portal1 = new Portal(dungeon, 2, 1, 1);
        Portal portal2 = new Portal(dungeon, 4, 4, 1);
        Boulder boulder = new Boulder(dungeon, 4, 4);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        dungeon.addEntity(boulder);

        player.moveTo(2, 1);
        assert(player.getX() == 2 && player.getY() == 1);
    }

    @Test
    // Test when a a portal pair is obstructed. Enemies should not teleport
    public void testPortalBlocking2() {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 1);
        Portal portal1 = new Portal(dungeon, 2, 1, 1);
        Portal portal2 = new Portal(dungeon, 4, 1, 1);
        Hunter hunter = new Hunter(dungeon, 5, 1);
        Boulder boulder = new Boulder(dungeon, 2, 1);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        dungeon.addEntity(hunter);
        dungeon.addEntity(boulder);
        player.attach(hunter);
        
        player.moveTo(0, 1);
        assert(hunter.getX() == 4 && hunter.getY() == 1);
    }

    @Test
    public void testMultiplePortals() {
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 1, 1);
        Portal portal1 = new Portal(dungeon, 2, 1, 1);
        Portal portal2 = new Portal(dungeon, 4, 1, 1);
        Portal portal3 = new Portal(dungeon, 2, 2, 2);
        Portal portal4 = new Portal(dungeon, 4, 2, 2);
        Portal portal5 = new Portal(dungeon, 2, 3, 3);
        Portal portal6 = new Portal(dungeon, 4, 3, 3);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        dungeon.addEntity(portal3);
        dungeon.addEntity(portal4);
        dungeon.addEntity(portal5);
        dungeon.addEntity(portal6);

        player.moveTo(2, 1);
        assert(player.getX() == 4 && player.getY() == 1);
        player.moveTo(2, 2);
        assert(player.getX() == 4 && player.getY() == 2);
        player.moveTo(2, 3);
        assert(player.getX() == 4 && player.getY() == 3);
        player.moveTo(4, 1);
        assert(player.getX() == 2 && player.getY() == 1);
        player.moveTo(4, 2);
        assert(player.getX() == 2 && player.getY() == 2);
        player.moveTo(4, 3);
        assert(player.getX() == 2 && player.getY() == 3);
    }

    @Test
    public void testBoulder() {
        Dungeon dungeon = new Dungeon(6, 6);
        Player player = new Player(dungeon, 0, 1);
        Boulder boulder = new Boulder(dungeon, 1, 1);
        Portal portal1 = new Portal(dungeon, 2, 1, 1);
        Portal portal2 = new Portal(dungeon, 4, 4, 1);

        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(boulder);
        dungeon.addEntity(player);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);

        player.moveRight();
        assert(player.getX() == 1 && player.getY() == 1);
        assert(boulder.getX() == 4 && boulder.getY() == 4);

        player.moveRight();
        assert(player.getX() == 4 && player.getY() == 4);
        assert(boulder.getX() == 5 && boulder.getY() == 4);
    }

    
}