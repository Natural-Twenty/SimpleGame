package unsw.dungeon;

import java.util.List;

/**
 * A boulder entity that can be moved by players but blocks enemies
 * @authour Frank Merriman, The Tran
 */
public class Boulder extends Entity implements MoveBehaviour{
    private Dungeon dungeon;

    /**
     * Creates a new boulder entity
     * @param dungeon that boulder is in
     * @param x axis of created boulder
     * @param y axis of created boulder
     */
    public Boulder (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }
    
    public void moveTo(int newX, int newY) {
        int oldX = this.getX();
        int oldY = this.getY();
        if (canMove(newX, newY)){
            x().set(newX);
            y().set(newY);
            changeFloorSwitchState(oldX, oldY, newX, newY);
        }
    }

    
    public boolean canMove(int newX, int newY) {
        if (
            newX < 0 ||
            newY < 0 ||
            newX > dungeon.getWidth() - 1 ||
            newY > dungeon.getHeight() - 1
        ) {
            return false;
        }

        List<Entity> checkTile = dungeon.getEntities(newX, newY);
        if (checkTile.size() < 1) {
            return true;
        }
        for (Entity e: checkTile) {
            if (e.isBarrier(this)) {
                // Something is blocking the boulder
                return false;
            } else {
                e.onCollide(this);
            }
        }
    
        return true;
    }
    
    /**
     * Updates floor switch states on any switches that have either
     * just been uncovered by the boulder or covered by the boulder
     * @param oldX x axis of previous location of boulder
     * @param oldY y axis of previous location of boulder
     * @param newX x axis of pending location of boulder
     * @param newY y axis of pending location of boulder
     */
    private void changeFloorSwitchState(int oldX, int oldY, int newX, int newY) {
        // Boulder is moving off the floor switch
        List<Entity> oldEntities = dungeon.getEntities(oldX, oldY);
        for (Entity e : oldEntities) {
            if (e == null) {
                continue;
            } else if (e instanceof FloorSwitch) {
                // Untrigger the floor switch
                FloorSwitch oldFloorSwitch = (FloorSwitch) e;
                oldFloorSwitch.untriggerFloorSwitch();
            }
        }
        // Boulder is moving onto a new floor switch
        List<Entity> newEntities = dungeon.getEntities(newX, newY);
        for (Entity ent : newEntities) {
            if (ent == null) {
                continue;
            } else if (ent instanceof FloorSwitch) {
                // Trigger the floor switch
                FloorSwitch newFloorSwitch = (FloorSwitch) ent;
                newFloorSwitch.triggerFloorSwitch();
            }
        }
    }

    @Override
    public boolean isBarrier(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            int x = player.computeXDirection(this);
            int y = player.computeYDirection(this);
            List<Entity> entities = dungeon.getEntities(x, y);
            for (Entity ent : entities) {
                if (ent.isBarrier(this)) {
                    // Boulder is blocked
                    return true;
                }
            }
            // Boulder is not blocked.
            return false;
        }
        // Entity trying to enter is not a player
        return true;
    }

    @Override
    public void onCollide(Entity entity) {
        // Ensure pusher is a player
        if (entity instanceof Player) {
            // Compute direction of push
            Player player = (Player) entity;
            int x = player.computePrevXDirection(this);
            int y = player.computePrevYDirection(this);
            moveTo(x, y);
        }
    }
}