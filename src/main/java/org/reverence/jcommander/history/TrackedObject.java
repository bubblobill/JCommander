package org.reverence.jcommander.history;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

public class TrackedObject<T> {

    private final Deque<T> undoHistory = new ArrayDeque<>();
    private final Deque<T> redoHistory = new ArrayDeque<>();
    private final List<ChangeListener> objectChangeListeners = new ArrayList<>();
    private final List<HistoryChangeListener> historyChangeListeners = new ArrayList<>();
    private boolean hasBeenSet = false;
    private T object = null;

    /**
     * Sets a new value for the tracked object and notifies listeners of the change.
     *
     * @param newObject the new value for the tracked object
     */
    public void set(T newObject) {
        if (!Objects.equals(object, newObject)) {
            redoHistory.clear();
            if (hasBeenSet) {
                undoHistory.push(object);
            }
            hasBeenSet = true;
            notifyAllHistoryChanged(new HistoryChangedEvent(this, true, false));
            object = newObject;
        }

        // It should be indistinguishable from a ChangeEventListener whether we actually changed the history or not.
        notifyAllObjectChanged(new ChangeEvent(this));
    }

    /**
     * Gets the current value of the tracked object.
     *
     * @return the current value of the tracked object
     */
    public T get() {
        return object;
    }

    /**
     * Undoes the last change to the tracked object and notifies listeners of the change.
     */
    public void undo() {
        T undid = undoHistory.pop();
        redoHistory.push(object);
        object = undid;
        notifyAllObjectChanged(new ChangeEvent(this));
        notifyAllHistoryChanged(new HistoryChangedEvent(this, !undoHistory.isEmpty(), true));
    }

    /**
     * Redoes the last undone change to the tracked object and notifies listeners of the change.
     */
    public void redo() {
        T redid = redoHistory.pop();
        undoHistory.push(object);
        object = redid;
        notifyAllObjectChanged(new ChangeEvent(this));
        notifyAllHistoryChanged(new HistoryChangedEvent(this, true, !redoHistory.isEmpty()));
    }

    private void notifyAllObjectChanged(ChangeEvent e) {
        for (ChangeListener listener : objectChangeListeners) {
            listener.stateChanged(e);
        }
    }

    private void notifyAllHistoryChanged(HistoryChangedEvent e) {
        for (HistoryChangeListener listener : historyChangeListeners) {
            listener.historyChanged(e);
        }
    }

    /**
     * Notifies all listeners about the current state of undo and redo operations.
     */
    public void notifyAllAboutHistory() {
        notifyAllHistoryChanged(new HistoryChangedEvent(this, !undoHistory.isEmpty(), !redoHistory.isEmpty()));
    }

    /**
     * Adds a ChangeListener to be notified of changes to the tracked object.
     *
     * @param listener the ChangeListener to be added
     */
    public void addChangeListener(ChangeListener listener) {
        objectChangeListeners.add(listener);
    }

    /**
     * Removes a ChangeListener from the list of listeners.
     *
     * @param listener the ChangeListener to be removed
     */
    public void removeChangeListener(ChangeListener listener) {
        objectChangeListeners.remove(listener);
    }

    /**
     * Adds a HistoryChangeListener to be notified of changes to the history of the tracked object.
     *
     * @param listener the HistoryChangeListener to be added
     */
    public void addHistoryChangeListener(HistoryChangeListener listener) {
        historyChangeListeners.add(listener);
    }

    /**
     * Removes a HistoryChangeListener from the list of history listeners.
     *
     * @param listener the HistoryChangeListener to be removed
     */
    public void removeHistoryChangeListener(HistoryChangeListener listener) {
        historyChangeListeners.remove(listener);
    }
}
