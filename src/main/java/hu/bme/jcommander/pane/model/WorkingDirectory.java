package hu.bme.jcommander.pane.model;

import hu.bme.jcommander.filesystem.Handle;
import hu.bme.jcommander.filesystem.RootHandle;
import hu.bme.jcommander.history.HistoryChangeListener;
import hu.bme.jcommander.history.TrackedObject;

import javax.swing.event.ChangeListener;

public class WorkingDirectory {

    private final TrackedObject<Handle> trackedDirectory = new TrackedObject<>();

    public void resetToRoot() {
        trackedDirectory.set(new RootHandle());
    }

    public void setTo(Handle directory) {
        if (directory.isLeaf()) {
            return;
        }

        trackedDirectory.set(directory);
    }

    public void addChangeListener(ChangeListener l) {
        trackedDirectory.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        trackedDirectory.removeChangeListener(l);
    }

    public void addHistoryChangeListener(HistoryChangeListener l) {
        trackedDirectory.addHistoryChangeListener(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener l) {
        trackedDirectory.removeHistoryChangeListener(l);
    }

    public Handle[] list() {
        return trackedDirectory.get().getChildren();
    }

    public String getAbsolutePath() {
        return trackedDirectory.get().getAbsolutePath();
    }

    public void selectParent() {
        setTo(trackedDirectory.get().getParent());
    }

    public void notifyAllAboutWdHistory() {
        trackedDirectory.notifyAllAboutHistory();
    }

    public void selectPrevious() {
        trackedDirectory.undo();
    }

    public void selectNext() {
        trackedDirectory.redo();
    }

    public boolean isRoot() {
        return trackedDirectory.get() instanceof RootHandle;
    }
}
