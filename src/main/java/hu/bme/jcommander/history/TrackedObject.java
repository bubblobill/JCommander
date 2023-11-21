package hu.bme.jcommander.history;

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

        // it should be indistinguishable from a ChangeEventListener whether we actually changed the history or not
        notifyAllObjectChanged(new ChangeEvent(this));
    }

    public T get() {
        return object;
    }

    public void undo() {
        T undid = undoHistory.pop();
        redoHistory.push(object);
        object = undid;
        notifyAllObjectChanged(new ChangeEvent(this));
        notifyAllHistoryChanged(new HistoryChangedEvent(this, !undoHistory.isEmpty(), true));
    }

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

    public void addChangeListener(ChangeListener l) {
        objectChangeListeners.add(l);
    }

    public void removeChangeListener(ChangeListener l) {
        objectChangeListeners.remove(l);
    }

    public void addHistoryChangeListener(HistoryChangeListener l) {
        historyChangeListeners.add(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener l) {
        historyChangeListeners.remove(l);
    }

    public void notifyAllAboutHistory() {
        notifyAllHistoryChanged(new HistoryChangedEvent(this, !undoHistory.isEmpty(), !redoHistory.isEmpty()));
    }
}
