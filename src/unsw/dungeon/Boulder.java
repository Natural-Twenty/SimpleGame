package unsw.dungeon;

import java.util.List;

public class Boulder extends Entity implements MoveBehaviour{
    private Dungeon dungeon;

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
    
    public void changeFloorSwitchState(int oldX, int oldY, int newX, int newY) {
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
            int x = computeXDirection(player);
            int y = computeYDirection(player);
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
            int y = computeYDirection(player);
            moveTo(x, y);
        }
    }

    private int computePrevXDirection(Player player) {
        int xDiff = getX() - player.getPrevX();
        return getX() + xDiff;
    }

    private int computePrevYDirection(Player player) {
        int yDiff = getY() - player.getPrevXY();
        return getY() + yDiff;
    }

    private int
}