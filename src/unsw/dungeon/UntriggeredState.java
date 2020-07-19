package unsw.dungeon;

public class UntriggeredState implements FloorSwitchState{
    FloorSwitch floorSwitch;

    public UntriggeredState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }
    public void triggerFloorSwitch() {
        floorSwitch.setState(floorSwitch.getTriggeredState());
    }

    public void untriggerFloorSwitch() {
        // do nothing
    }
}