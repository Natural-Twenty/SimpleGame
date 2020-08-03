package unsw.dungeon;

public class Water extends Entity {
    private Dungeon dungeon;
    public Water(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    @Override
    public boolean isBarrier(Entity e) {
        if (e instanceof Boulder) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Boulder) {
            // Boulder boulder = (Boulder) e;
            // boulderCollision(boulder);
            
            dungeon.removeEntity(this);
            setDisplayOnScreen(false);
            dungeon.removeEntity(e);
            e.setDisplayOnScreen(false);
        }
    }
}