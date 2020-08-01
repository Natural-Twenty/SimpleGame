package unsw.dungeon;

public class HunterFearState implements HunterState{
    private Hunter hunter;
    private boolean defeated;

    public HunterFearState(Hunter hunter) {
        this.hunter = hunter;
        this.defeated = false;
    }

    public void normaliseHunter(){
        hunter.setState(hunter.getNormalState());
    }

    public void fearHunter() {
        
    }

    public void killHunter() {
        hunter.setState(hunter.getDeadState());
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void moveHorizontal(int currX, int currY, int targetX, int targetY) {
        if (targetX < currX) {
            hunter.moveTo(currX + 1, currY);
        } else {
            hunter.moveTo(currX - 1, currY);
        }
    }

    public void moveVertical(int currX, int currY, int targetX, int targetY) {
        if (targetY < currY) {
            hunter.moveTo(currX, currY + 1);
        } else {
                hunter.moveTo(currX, currY - 1);
        }
    }



}