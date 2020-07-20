package unsw.dungeon;
/**
 * A DoorState interface
 * 
 * An interface for the states of the door
 * @author Frank Merriman, The Tran
 */
public interface DoorState {
    
    /**
     * Changes the door to an open state (opens the door)
     */
    public void openDoor();

    /**
     * Changes the door to a closed state (closes the door)
     */
    public void closeDoor();
}