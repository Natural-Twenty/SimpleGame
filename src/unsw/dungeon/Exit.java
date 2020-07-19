package unsw.dungeon;

public class Exit extends Entity implements Goal {

    private boolean completed;

    public Exit(int x, int y) {
        super(x, y);
        completed = false;
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            completed = true;
        }
    }

    @Override
    public boolean isComplete() {
        // TODO Auto-generated method stub
        return false;
    }
    
}