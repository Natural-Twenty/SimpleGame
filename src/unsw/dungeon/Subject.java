package unsw.dungeon;
/**
 * Subject interface
 * 
 * An interface for subjects to be observed by Observers.
 * 
 * @author Frank Merriman, The Tran
 */
public interface Subject {

    /**
     * Add an observer to the subject.
     * @param o An observer to add
     */
    public void attach(Observer o);

    /**
     * Remove an observer from a subject.
     * @param o An observer to remove
     */
    public void detach(Observer o);
    
    /**
     * Notify all observers that are attached to subject
     */
    public void updateObservers();
}