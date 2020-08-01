package unsw.dungeon;

public class HunterDeadState implements HunterState{
    private Hunter hunter;
    private boolean defeated;

    public HunterDeadState(Hunter hunter) {
        this.hunter = hunter;
        this.defeated = true;
    }

    public void normaliseHunter(){
        
    }

    public void fearHunter() {
        
    }

    public void killHunter() {
        
    }

    public void reviveHunter() {
        hunter.setState(hunter.getNormalState());
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void moveVertical(int currX, int currY, int targetX, int targetY) {

    }

    public void moveHorizontal(int currX, int currY, int targetX, int targetY) {

    }

}