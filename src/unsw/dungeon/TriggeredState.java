package unsw.dungeon;
/**
 * A FloorSwtichState that represents the triggered state of a floor
 * switch.
 */
public class TriggeredState implements FloorSwitchState {
    private FloorSwitch floorSwitch;
    /**
     * A constructor for a triggered floor switch state. Created 
     * automatically when a floor switch is created.
     * @param floorSwitch
     */
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