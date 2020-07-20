package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Hunter extends Entity implements MoveBehaviour, Observer, Goal, Subject{

    private Dungeon dungeon;
    private boolean defeated;
    private List<Observer> listObservers;

    public Hunter(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.defeated = false;
        listObservers = new ArrayList<>();
    }

    /**
     * Each hunter will be stored within a GoalAND list of goal
     * When they are killed by player they are marked as complete
     */
    @Override
    public boolean isComplete() {
        return defeated;
    }

    @Override
    public void detach(Observer o) {
        listObservers.remove(o);
    }

    @Override
    public void attach(Observer o) {
        listObservers.add(o);
    }

    @Override
    public void updateObservers() {
        for (Observer o : listObservers) {
            o.update(this);
        }
    }

    @Override
    public void update(Object obj) {
        if (obj instanceof Player) {
            update( (Player) obj);
        }
    }

    //Make sure that code that auto attaches enemy observers to Player does so after all entities added from json (maybe when adding goals?)
    public void update(Player player) {
        int targetX = player.getX();
        int targetY = player.getY();
        boolean fearPlayer = player.isInvincible();
        findClosestPath(targetX, targetY, fearPlayer);
    }

    /**
     * Given a target coordinate it will attempt to move in a straight line towards it
     * It will always move along the axis that has the smallest difference between target and current
     * If both are the same it prefers moving horizontally (even if both diff are 0)
     * The enemy has reverse behaviour when the player is under potion influence
     * @param targetX int giving location on x axis
     * @param targetY int giving location on y axis
     */
    public void findClosestPath(int targetX, int targetY, boolean fearPlayer) {
        int currX = getX();
        int currY = getY();

        int xDiff = Math.abs(targetX - currX);
        int yDiff = Math.abs(targetY - currY);

        // Moves away if fearPlayer is true
        if (xDiff == 0 && yDiff == 0) {
            //do nothing
        } else if (yDiff == 0) {
            moveHorizontal(currX, currY, targetX, targetY, fearPlayer);
        } else if (xDiff == 0) {
            moveVertical(currX, currY, targetX, targetY, fearPlayer);
        } else if (xDiff <= yDiff) {
            moveHorizontal(currX, currY, targetX, targetY, fearPlayer);
        } else if (yDiff < xDiff) {
            moveVertical(currX, currY, targetX, targetY, fearPlayer);
        }
        

    }
    
    public void moveHorizontal(int currX, int currY, int targetX, int targetY, boolean fearPlayer) {
        if (fearPlayer == false) {
            if (targetX < currX) {
                moveTo(currX - 1, currY);
            } else {
                moveTo(currX + 1, currY);
            }
        } else {
            if (targetX < currX) {
                moveTo(currX + 1, currY);
            } else {
                moveTo(currX - 1, currY);
            }
        }
    }

    public void moveVertical(int currX, int currY, int targetX, int targetY, boolean fearPlayer) {
        if (fearPlayer == false) {
            if (targetY < currY) {
                moveTo(currX, currY - 1);
            } else {
                moveTo(currX, currY + 1);
            }
        } else {
            if (targetY < currY) {
                moveTo(currX, currY + 1);
            } else {
                moveTo(currX, currY - 1);
            }
        }
    }

    @Override
    public void moveTo(int x, int y) {
        if (canMove(x, y)) {
            x().set(x);
            y().set(y);
            collide(x, y);
        }
        
    }

    @Override
    public boolean canMove(int x, int y) {

        if (
            x < 0 ||
            y < 0 ||
            x > dungeon.getWidth() - 1 ||
            y > dungeon.getHeight() - 1
        ) {
            return false;
        }

        List<Entity> tileEntities = dungeon.getEntities(x, y);

        if (tileEntities.size() < 1) {
            return true;
        }
        
        for (Entity e : tileEntities) {
            if (e.isBarrier(this)) {
                return false;
            } else {
                //e.onCollide(this);
            }
        }
        
        return true;
    }

    public void defeat() {
        defeated = true;
        updateObservers();
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;

            if(p.canFight(this)) {
                dungeon.removeEntity(this);
                defeat();
            } else {
                dungeon.removeEntity(p);
            }

        }
    }

    private void collide(int x, int y) {
        List<Entity> entities = dungeon.getEntities(x, y);
        for (Entity e : entities) {
            if (e != null) {
                e.onCollide(this);
            }
        }
    }

}