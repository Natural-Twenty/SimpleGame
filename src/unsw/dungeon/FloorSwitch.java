package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class FloorSwitch extends Entity implements Goal, Subject{
    private FloorSwitchState triggeredState;
    private FloorSwitchState untriggeredState;

    private FloorSwitchState currState;

    private List<Observer> listObservers;

    Dungeon dungeon;
    
    public FloorSwitch(Dungeon dungeon, int x, int y) {
        super(x,y);
        triggeredState = new TriggeredState(this);
        untriggeredState = new UntriggeredState(this);
        this.dungeon =dungeon;
        currState = checkBoulder(dungeon, x, y);
        this.listObservers = new ArrayList<>();
    }

    public void triggerFloorSwitch() {
        currState.triggerFloorSwitch();
        updateObservers();
    }

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

    void setState(FloorSwitchState state) {
        this.currState = state;
    }

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