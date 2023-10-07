package jcommander.pane.model;

import jcommander.history.HistoryChangeListener;
import jcommander.history.TrackedObject;

import javax.swing.event.ChangeListener;
import java.io.File;

public class WorkingDirectory {

    private final TrackedObject<File> trackedDirectory = new TrackedObject<>(null);

    public void set(File directory) {
        trackedDirectory.set(directory);
    }

    public void addChangeListener(ChangeListener l) {
        trackedDirectory.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        trackedDirectory.removeChangeListener(l);
    }

    public void addHistoryChangeListener(HistoryChangeListener<File> l) {
        trackedDirectory.addHistoryChangeListener(l);
    }

    public void removeHistoryChangeListener(HistoryChangeListener<File> l) {
        trackedDirectory.removeHistoryChangeListener(l);
    }

    // this is awfully bad
    public boolean isRoot() {
        return trackedDirectory.get() == null;
    }

    public File[] list() {
        if (isRoot()) { // root has the mount points as its directories
            return File.listRoots();
        }
        File[] files = trackedDirectory.get().listFiles();
        if (files == null) {
            return new File[0];
        }
        return files;
    }

    public String getAbsolutePath() {
        if (isRoot()) { // root's path is an empty string
            return "";
        }
        return trackedDirectory.get().getAbsolutePath();
    }

    public void selectParent() {
        if (isRoot()) { // root does not have a parent directory
            return;
        }
        set(trackedDirectory.get().getParentFile());
    }

    // See the repeating pattern?
    // if (isRoot())
    //     thenDo();
    // else
    //     otherwiseDo();

    public void selectPrevious() {
        trackedDirectory.undo();
    }

    public void selectNext() {
        trackedDirectory.redo();
    }

    // This is unrelated. Why is it here?
    private static File createFileFromStringPath(String url) {
        if (url.isEmpty()) {
            return null;
        }
        return new File(url);
    }
}
