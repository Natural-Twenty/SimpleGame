package unsw.dungeon;

public class OpenDoorState implements DoorState{
    private Door door;

    public OpenDoorState(Door door) {
        this.door = door;
    }
    public void openDoor() {

    }

    public void closeDoor() {
        door.setState(door.getClosedState());
    }
}