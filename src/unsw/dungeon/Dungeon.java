/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Return all entities at the given coordinates in the dungeon
     * @param x
     * @param y
     * @return null if no entity at location otherwise a list of type entity
     */
    public List<Entity> getEntities(int x, int y) {
        List<Entity> tileEntities= new ArrayList<>();

        for (Entity e : entities) {
            if (e != null) {
                int eX = e.getX();
                int eY = e.getY();

                if (x == eX && y == eY) {
                    tileEntities.add(e);
                }
            }
        }
        
        if (tileEntities.size() > 0) {
            return tileEntities;
        }

        return null;
    }
}
