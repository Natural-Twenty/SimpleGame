package unsw.dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public void moveUp() {
        if (getY() > 0 && canEnterTile(getX(), getY() - 1))
            y().set(getY() - 1);
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1 && canEnterTile(getX(), getY() + 1))
            y().set(getY() + 1);
    }

    public void moveLeft() {
        if (getX() > 0 && canEnterTile(getX() - 1, getY()))
            x().set(getX() - 1);
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && canEnterTile(getX() + 1, getY()))
            x().set(getX() + 1);
    }

    /**
     * This method takes in an x and y coordinate and then checks the dungeon if there is a wall that matches
     * those coordinates (will need to update to include doors)
     * @param x cooridnate of tile we are checking
     * @param y cooridnate of tile we are checking
     * @return true if eligible tile for player movement
     */
    public boolean canEnterTile(int x, int y) {
        Entity tile = dungeon.getEntity(x, y);

        if (tile == null) { //if null there is no entity block the tile so we can move there
            return true;
        }

        

        
    }
}
