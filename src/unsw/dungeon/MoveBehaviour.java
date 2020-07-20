package unsw.dungeon;


/**
 * Interface for allowing an object to implement certain methods
 * associated with moving in a 2D space
 * @author Frank Merriman, The Tran
 *
 */
public interface MoveBehaviour {


    /**
     * Given two ints, moves object implementing MoveBehaviour to
     * those respective coordinates
     * @param x x axis value
     * @param y y axis value
     */
    public void moveTo(int x, int y);

    /**
     * Given a target destination via two ints, checks if object
     * implementing MoveBehaviour can move to those respective coordinates
     * @return true if object can enter those coordinates otherwise false
     */
    public boolean canMove(int x, int y);

}