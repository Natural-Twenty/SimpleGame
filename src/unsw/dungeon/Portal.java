package unsw.dungeon;

import java.util.List;
/**
 * A Portal class that represnts a portal in the dungeon.
 * 
 * @auther Frank Merriman, The Tran
 */
public class Portal extends Entity {
    private int u_id;
    private Dungeon dungeon;
    /**
     * A Portal constructor to create a portal in the dungeon.
     * @param dungeon The dungeon the portal belongs to
     * @param x x-coordinate of the portal to appear in
     * @param y y-coordinate of the portal to appear in
     * @param u_id Unique ID of the portal to link to another portal of
     * the same ID.
     */
    public Portal(Dungeon dungeon, int x, int y, int u_id) {
        super(x, y);
        this.dungeon = dungeon;
        this.u_id = u_id;
    }

    public int getID() {
        return u_id;
    }

    @Override
    public void onCollide(Entity e) {
        if(e instanceof MoveBehaviour) {
            Portal portalPair = findPair();
            int teleportX = portalPair.getX();
            int teleportY = portalPair.getY();
            teleport(e, teleportX, teleportY);
        }
    }

    /**
     * Looks through dungeon for a different portal with
     * the same id
     * @return portal if it exits otherwise null
     */
    private Portal findPair() {
        List<Entity> entities = dungeon.getAllEntities();
        for (Entity ent : entities) {
            if (ent == null) {
                continue;
            }
            Portal portalPair = getPortalPair(ent);
            if (portalPair != null) {
                return portalPair;
            }
        }
        // We are here because we cannot find the other portal
        return null;
    }

    /**
     * For a given portal checks if this is its matching portal
     * @return this if is the pair otherwise null
     */
    private Portal getPortalPair(Entity e) {
        if (e instanceof Portal && e != this) {
            Portal portalPair = (Portal) e;
            if (portalPair.equalID(this.u_id)) {
                return portalPair;
            }
        }
        return null;
    }
    /**
     * Checks if the provided u_id is equal to the portal caller's u_id.
     * @param u_id The ID of another portal.
     * @return True if IDs match, otherwise false.
     */
    public boolean equalID(int u_id) {
        if (this.u_id == u_id) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isBarrier(Entity e) {
         if (e instanceof MoveBehaviour || e instanceof Portal) {
             return false;
         } else {
             return true;
         }
        
        
    }

    private void teleport(Entity e, int x, int y) {
        if (!otherSideBlocked(x, y)) {
            e.setX(x);
            e.setY(y);
        }
        
    }

    private boolean otherSideBlocked(int x, int y) {
        List<Entity> entities = dungeon.getEntities(x, y);
        for (Entity e: entities) {
            if (e == null) {
                continue;
            } else if (e.isBarrier(this)) {
                // Something blocking the portal
                return true;
            } else {
            }
        }
        // We are here because there is nothing blocking the portal.
        return false;
    }
}