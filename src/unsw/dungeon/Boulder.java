package unsw.dungeon;

import java.util.List;

public class Boulder extends Entity implements MoveBehaviour{
    private Dungeon dungeon;

    public Boulder (Dungeon dungeon, int x, int y) {
        super(x,y);
        this.dungeon = dungeon;
    }

    public void moveTo(int newX, int newY) {
        if (canMove(newX, newY)){
            x().set(newX);
            y().set(newY);
        }
        FloorSwitch floorSwitch = checkFloorSwitch(newX, newY);

        
    }

    public boolean canMove(int newX, int newY) {
        List<Entity> checkTile = dungeon.getEntities(newX, newY);
        if (checkTile.size() < 1) {
            return true;
        }
        for (Entity e: checkTile) {
            if (e.isBarrier()) {
                // Something is blocking the boulder
                return false;
            } else {
                e.onCollide(this);
            }
        }
    
        return true;
    }
    
    private FloorSwitch checkFloorSwitch(int x, int y) {
        for (Entity e: )

        }
    }
}