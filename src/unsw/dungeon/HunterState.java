package unsw.dungeon;

public interface HunterState {
    public void normaliseHunter();
    public void killHunter();
    public void fearHunter();
    public boolean isDefeated();
    public void moveHorizontal(int currX, int currY, int targetX, int targetY);
    public void moveVertical(int currX, int currY, int targetX, int targetY);
}