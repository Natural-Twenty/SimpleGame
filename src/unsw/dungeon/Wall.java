package unsw.dungeon;

/**
 * A entity that blocks all entities from passing
 * @author Frank Merriman, The Tran
 */
public class Wall extends Entity{

    /**
     * Creates a new wall at given location
     * @param x axis location
     * @param y axis location
     */
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isBarrier(Entity e) {
        return true;
    }
}
