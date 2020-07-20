package unsw.dungeon;
/**
 * An UntriggeredFloorSwitchState that represents the untriggered 
 * state of a floor switch
 */
public class UntriggeredState implements FloorSwitchState{
    private FloorSwitch floorSwitch;
    /**
     * A constructor for an untriggered floor switch state. Automatically
     * created when a floor switch is created.
     * @param floorSwitch
     */
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