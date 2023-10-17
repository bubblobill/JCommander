package jcommander.pane.model;

import jcommander.filesystem.handle.Handle;
import jcommander.history.HistoryChangeListener;
import jcommander.history.TrackedObject;

import javax.swing.event.ChangeListener;
import java.io.File;

public class WorkingDirectory {

    private final TrackedObject<Handle> trackedDirectory = new TrackedObject<>(null);

    public void set(Handle directory) {
        trackedDirectory.set(directory);
    }

    public void addChangeListener(ChangeListener l) {
        trackedDirectory.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        trackedDirectory.removeChangeListener(l);
    }

    public void addHistoryChangeListener(HistoryChangeListener<Handle> l) {
        trackedDirectory.addHistoryChangeListener(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener<Handle> l) {
        trackedDirectory.removeHistoryChangeListener(l);
    }

    public Handle[] list() {
        return trackedDirectory.get().getChildren();
    }

    public String getAbsolutePath() {
        return trackedDirectory.get().getAbsolutePath();
    }

    public void selectParent() {
        set(trackedDirectory.get().getParent());
    }

    public void selectPrevious() {
        trackedDirectory.undo();
    }

    public void selectNext() {
        trackedDirectory.redo();
    }
}
