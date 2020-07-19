package unsw.dungeon;

import java.util.List;

public class FloorSwitch extends Entity {
    private FloorSwitchState triggeredState;
    private FloorSwitchState untriggeredState;

    private FloorSwitchState currState;

    Dungeon dungeon;
    
    public FloorSwitch(Dungeon dungeon, int x, int y) {
        super(x,y);
        triggeredState = new TriggeredState(this);
        untriggeredState = new UntriggeredState(this);
        this.dungeon =dungeon;
        currState = checkBoulder(dungeon, x, y);
    }

    public void triggerFloorSwitch() {
        currState.triggerFloorSwitch();
    }

    public void untriggerFloorSwitch() {
        currState.untriggerFloorSwitch();
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
        if (entities == null) {
            return untriggeredState;
        }
        for (Entity e : entities) {
            if (e instanceof Boulder) {
                return triggeredState;
            }
        }
        return untriggeredState;
    }
}