package unsw.dungeon;

import java.util.List;

public class Portal extends Entity {
    private int u_id;
    private Dungeon dungeon;

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

    public Portal findPair() {
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

    public Portal getPortalPair(Entity e) {
        if (e instanceof Portal && e != this) {
            Portal portalPair = (Portal) e;
            if (portalPair.equalID(this.u_id)) {
                return portalPair;
            }
        }
        return null;
    }

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