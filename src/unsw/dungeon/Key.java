package unsw.dungeon;


/**
 * A entity that can be used by the player to open a specific door
 * @author Frank Merriman, The Tran
 */
public class Key extends Entity {
    private int u_id;
    
    /**
     * Creates a new key with a unique id at given location
     * @param x axis location
     * @param y axis location
     * @param u_id unique id(corresponds to a door)
     */
    public Key(int x, int y, int u_id) {
        super(x, y);
        this.u_id = u_id;
        
    }

    public int getID() {
        return u_id;
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            Key playerKey = player.getKey();

            if(playerKey == null) {
                player.equip(this);
                setDisplayOnScreen(false);
            }
        }
    }
    
}