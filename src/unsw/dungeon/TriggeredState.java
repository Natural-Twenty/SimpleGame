package unsw.dungeon.FloorSwitch;

public class TriggeredState implements FloorSwitchState {
    FloorSwitch floorSwitch;

    public TriggeredState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }

    public void triggerFloorSwitch() {
       // do nothing, its already triggered. 
       return;
    }

    public void untriggerFloorSwitch() {
        floorSwitch.setState(floorSwitch.getUntriggeredState());
    }
}