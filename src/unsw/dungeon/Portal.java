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
        if(e instanceof Player) {
            Player player = (Player) e;
            Portal portalPair = findPair();
            int teleportX = portalPair.getX();
            int teleportY = portalPair.getY();
            player.moveTo(teleportX, teleportY);
            //teleport(teleportX, teleportY);
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
        if (e instanceof Player) {
            return false;
        } else {
            return true;
        }
        
    }

    // private void teleport(Entity e) {
    //     e.
    // }

    
}