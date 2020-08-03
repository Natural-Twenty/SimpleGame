package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/**
 * A basic enemy that pursues the enemy in straight lines, even if it means
 * getting stuck on a wall
 * @author Frank Merriman, The Tran
 */
public class Hunter extends Entity implements MoveBehaviour, Observer, Goal, Subject{

    private Dungeon dungeon;
    private List<Observer> listObservers;

    private HunterState normalState;
    private HunterState fearedState;
    private HunterState deadState;

    private HunterState currState;

    public Hunter(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        listObservers = new ArrayList<>();

        normalState = new HunterNormalState(this);
        fearedState = new HunterFearState(this);
        deadState = new HunterDeadState(this);
        currState = normalState;
    }

    /**
     * Each hunter will be stored within a GoalAND list of goal
     * When they are killed by player they are marked as complete
     */
    @Override
    public boolean isComplete() {
        return currState.isDefeated();
    }

    /**
     * Remove an observer from a subject.
     * @param o An observer to remove
     */
    @Override
    public void detach(Observer o) {
        listObservers.remove(o);
    }

    /**
     * Add an observer to the subject.
     * @param o An observer to add
     */
    @Override
    public void attach(Observer o) {
        listObservers.add(o);
    }

    /**
     * Notify all observers that are attached to subject
     */
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

    /**
     * Moves the Hunter in response to the position of the player
     * and whether they are invincible
     * @param player who we base movement off of
     */
    public void update(Player player) {
        int targetX = player.getX();
        int targetY = player.getY();
        if (player.getPotion() != null) {
            fear();
        } else {
            normalise();
        }
        findClosestPath(targetX, targetY);
    }

    /**
     * Given a target coordinate it will attempt to move in a straight line towards it
     * It will always move along the axis that has the smallest difference between target and current
     * If both are the same it prefers moving horizontally (even if both diff are 0)
     * The enemy has reverse behaviour when the player is under potion influence
     * @param targetX int giving location on x axis
     * @param targetY int giving location on y axis
     */
    private void findClosestPath(int targetX, int targetY) {
        int currX = getX();
        int currY = getY();

        int xDiff = Math.abs(targetX - currX);
        int yDiff = Math.abs(targetY - currY);

        // Moves away if fearPlayer is true
        if (xDiff == 0 && yDiff == 0) {
            //do nothing
        } else if (yDiff == 0) {
            currState.moveHorizontal(currX, currY, targetX, targetY);
        } else if (xDiff == 0) {
            currState.moveVertical(currX, currY, targetX, targetY);
        } else if (xDiff <= yDiff) {
            currState.moveHorizontal(currX, currY, targetX, targetY);
        } else if (yDiff < xDiff) {
            currState.moveVertical(currX, currY, targetX, targetY);
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
        currState.killHunter();
        updateObservers();
    }

    public void fear() {
        currState.fearHunter();
    }

    public void normalise() {
        currState.normaliseHunter();
    }

    public void setState(HunterState state) {
        currState = state;
    }

    public HunterState getNormalState() {
        return normalState;
    }

    public HunterState getFearedState() {
        return fearedState;
    }

    public HunterState getDeadState() {
        return deadState;
    }
    
    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;

            if(p.canFight(this)) {
                dungeon.removeEntity(this);
                defeat();
                setDisplayOnScreen(false);
            } else {
                dungeon.removeEntity(p);
                p.setDisplayOnScreen(false);
                dungeon.setlevelComplete(2); //level failed
            }

        }
    }

    /**
     * Runs the oncollide methods for any entity in the given coordinates
     * passing Hunter as the object
     * @param x axis location
     * @param y axis location
     */
    private void collide(int x, int y) {
        List<Entity> entities = dungeon.getEntities(x, y);
        for (Entity e : entities) {
            if (e != null) {
                e.onCollide(this);
            }
        }
    }

    @Override
    public String getGoalString(int indent) {
        String str = "";
        for (int i = 0; i < indent - 1; i++) { // -1 because will always be in an and
            str = str + "--";
        }
        str = str+"> Kill all enemies";
        return str;
    }

}