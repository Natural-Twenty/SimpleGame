package unsw.dungeon.FloorSwitch;

public class FloorSwitch {
    FloorSwitchState triggeredState;
    FloorSwitchState untriggeredState;

    FloorSwitchState currState;
    
    public FloorSwitch() {
        triggeredState = new TriggeredState(this);
        untriggeredState = new UntriggeredState(this);
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
}