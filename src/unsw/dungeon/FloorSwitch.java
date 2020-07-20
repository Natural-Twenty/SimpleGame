package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
/**
 * A FloorSwitch class that represents a floor switch in the dungeon.
 * It uses a state design pattern.
 * 
 * @author Frank Merriman, The Tran
 */
public class FloorSwitch extends Entity implements Goal, Subject{
    private FloorSwitchState triggeredState;
    private FloorSwitchState untriggeredState;

    private FloorSwitchState currState;

    private List<Observer> listObservers;

    Dungeon dungeon;
    /**
     * A constructor for FloorSwitch.
     * @param dungeon Dungeon where the floor switch belongs
     * @param x x-coordinate of the floor switch to appear
     * @param y y-coordinate of the floor switch to appear
     */
    public FloorSwitch(Dungeon dungeon, int x, int y) {
        super(x,y);
        triggeredState = new TriggeredState(this);
        untriggeredState = new UntriggeredState(this);
        this.dungeon =dungeon;
        currState = checkBoulder(dungeon, x, y);
        this.listObservers = new ArrayList<>();
    }
    /**
     * Triggers the floor switch and notifies observers
     */
    public void triggerFloorSwitch() {
        currState.triggerFloorSwitch();
        updateObservers();
    }
    /**
     * Untriggers the floor switch and notifies observers
     */
    public void untriggerFloorSwitch() {
        currState.untriggerFloorSwitch();
        updateObservers();
    }

    public FloorSwitchState getState() {
        return currState;
    }

    public FloorSwitchState getTriggeredState() {
        return triggeredState;
    }

    public FloorSwitchState getUntriggeredState() {
        return untriggeredState;
    }

    public void setState(FloorSwitchState state) {
        this.currState = state;
    }
    /**
     * Checks if floor switch is triggered
     * @return True if triggered, false if not
     */
    public boolean isTriggered() {
        if (currState == triggeredState) {
            return true;
        } else {
            return false;
        }
    }

    private FloorSwitchState checkBoulder(Dungeon dungeon, int x, int y) {
        List<Entity> entities = dungeon.getEntities(x, y);
        // if (entities == null) {
        //     return untriggeredState;
        // }
        for (Entity e : entities) {
            if (e != null && e instanceof Boulder) {
                return triggeredState;
            }
        }
        return untriggeredState;
    }


    @Override
    public boolean isComplete() {
        return isTriggered();
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
}