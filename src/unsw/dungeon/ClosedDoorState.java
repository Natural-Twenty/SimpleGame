package unsw.dungeon;

public class ClosedDoorState implements DoorState {
    private Door door;

    public ClosedDoorState(Door door) {
        this.door = door;
    }

    public void openDoor() {
        door.setState(door.getOpenState());
    }

    public void closeDoor() {
        
    }
}