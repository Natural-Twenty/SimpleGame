package unsw.dungeon;

public interface MoveBehaviour {

    public void moveTo(int x, int y);
    public boolean canMove(int x, int y);

}