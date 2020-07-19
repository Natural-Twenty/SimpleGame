package unsw.dungeon;

public class FloorSwitch extends Entity{
    FloorSwitchState triggeredState;
    FloorSwitchState untriggeredState;

    FloorSwitchState currState;

    Dungeon dungeon;
    
    public FloorSwitch(Dungeon dungeon, int x, int y) {
        super(x,y);
        triggeredState = new TriggeredState(this);
        untriggeredState = new UntriggeredState(this);
        this.dungeon =dungeon;
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
}