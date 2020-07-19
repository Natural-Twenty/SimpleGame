package unsw.dungeon;

import java.util.List;
import java.lang.Math;

public class Hunter extends Entity implements MoveBehaviour, Observer{

    private Dungeon dungeon;

    public Hunter(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    @Override
    public void update(Object obj) {
        if (obj instanceof Player) {
            update( (Player) obj);
        }
    }

    public void update(Player player) {
        int targetX = player.getX();
        int targetY = player.getY();
        findClosestPath(targetX, targetY);
    }

    /**
     * Given a target coordinate it will attempt to move in a straight line towards it
     * It will always move along the axis that has the smallest difference between target and current
     * If both are the same it prefers moving horizontally
     * @param targetX int giving location on x axis
     * @param targetY int giving location on y axis
     */
    public void findClosestPath(int targetX, int targetY) {
        int currX = getX();
        int currY = getY();

        int xDiff = Math.abs(targetX - currX);
        int yDiff = Math.abs(targetY - currY);
        //there could be issues around xdiff or ydiff being zero deal in morning

        if (xDiff <= yDiff) {
            if (targetX < currX) {
                moveTo(currX - 1, currY);
            } else {
                moveTo(currX + 1, currY);
            }
        } else {
            if (targetY < currY) {
                moveTo(currX, currY - 1);
            } else {
                moveTo(currX, currY + 1);
            }
        }
        
    }

    @Override
    public void moveTo(int x, int y) {
        if (canMove(x, y)) {
            x().set(x);
            y().set(y);
        }
        
    }

    @Override
    public boolean canMove(int x, int y) {
        List<Entity> tileEntities = dungeon.getEntities(x, y);

        if (tileEntities.size() < 1) {
            return true;
        }
        
        for (Entity e : tileEntities) {
            if (e.isBarrier(this)) {
                return false;
            } else {
                e.onCollide(this);
            }
        }
        
        return true;
    }

}