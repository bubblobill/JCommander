package org.reverence.jcommander.pane.model;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.filesystem.Handle;
import org.reverence.jcommander.filesystem.RootHandle;
import org.reverence.jcommander.history.HistoryChangeListener;
import org.reverence.jcommander.history.TrackedObject;

import javax.swing.event.ChangeListener;

public class WorkingDirectory {

    private final TrackedObject<Handle> trackedDirectory = new TrackedObject<>();

    /**
     * Resets the working directory to the root directory.
     */
    public void resetToRoot() {
        trackedDirectory.set(new RootHandle());
    }

    /**
     * Sets the working directory to the specified directory if it is not a leaf node.
     *
     * @param directory the directory to set as the working directory.
     */
    public void setTo(Handle directory) {
        if (directory.isLeaf()) {
            return;
        }

        trackedDirectory.set(directory);
    }

    /**
     * Adds a ChangeListener to listen for changes in the working directory.
     *
     * @param listener the ChangeListener to be added.
     */
    public void addChangeListener(ChangeListener listener) {
        trackedDirectory.addChangeListener(listener);
    }

    /**
     * Removes a ChangeListener from the list of listeners.
     *
     * @param listener the ChangeListener to be removed.
     */
    public void removeChangeListener(ChangeListener listener) {
        trackedDirectory.removeChangeListener(listener);
    }

    /**
     * Adds a HistoryChangeListener to listen for changes in the working directory's history.
     *
     * @param listener the HistoryChangeListener to be added.
     */
    public void addHistoryChangeListener(HistoryChangeListener listener) {
        trackedDirectory.addHistoryChangeListener(listener);
    }

    /**
     * Removes a HistoryChangeListener from the list of history listeners.
     *
     * @param listener the HistoryChangeListener to be removed.
     */
    public void removeHistoryChangeListener(HistoryChangeListener listener) {
        trackedDirectory.removeHistoryChangeListener(listener);
    }

    /**
     * Returns an array of Handles representing the children of the current working directory.
     *
     * @return an array of Handles representing the children of the current working directory.
     */
    public Handle[] list() {
        return trackedDirectory.get().getChildren();
    }

    /**
     * Returns the absolute path of the current working directory.
     *
     * @return the absolute path of the current working directory.
     */
    public String getAbsolutePath() {
        return trackedDirectory.get().getAbsolutePath();
    }

    /**
     * Sets the working directory to its parent directory.
     */
    public void selectParent() {
        setTo(trackedDirectory.get().getParent());
    }

    /**
     * Notifies all listeners about changes in the working directory's history.
     */
    public void notifyAllAboutWdHistory() {
        trackedDirectory.notifyAllAboutHistory();
    }

    /**
     * Undoes the last change in the working directory's history.
     */
    public void selectPrevious() {
        trackedDirectory.undo();
    }

    /**
     * Redoes the last undone change in the working directory's history.
     */
    public void selectNext() {
        trackedDirectory.redo();
    }

    /**
     * Checks if the current working directory is the root directory.
     *
     * @return true if the current working directory is the root directory, false otherwise.
     */
    public boolean isRoot() {
        return trackedDirectory.get() instanceof RootHandle;
    }
}
