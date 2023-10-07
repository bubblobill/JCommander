package jcommander.pane.model;

import java.io.File;
import java.util.*;

public class WorkingDirectory {

    private File directory = null;

    private final Deque<String> undoHistory = new ArrayDeque<>();
    private final Deque<String> redoHistory = new ArrayDeque<>();

    private final List<DirectoryChangeListener> listeners = new ArrayList<>();

    public void set(File directory) {
        redoHistory.clear();
        undoHistory.push(getAbsolutePath());
        setAndNotify(directory);
    }

    private void setAndNotify(File directory) {
        this.directory = directory;
        notifyAllWorkingDirectoryChanged(new DirectoryChangedEvent(this));
    }

    private void notifyAllWorkingDirectoryChanged(DirectoryChangedEvent e) {
        for (DirectoryChangeListener listener : listeners) {
            listener.directoryChanged(e);
        }
    }

    public void addDirectoryChangeListener(DirectoryChangeListener l) {
        listeners.add(l);
    }

    public void removeDirectoryChangeListener(DirectoryChangeListener l) {
        listeners.remove(l);
    }

    // this is awfully bad
    public boolean isRoot() {
        return directory == null;
    }

    public File[] list() {
        if (isRoot()) { // root has the mount points as its directories
            return File.listRoots();
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return new File[0];
        }
        return files;
    }

    public String getAbsolutePath() {
        if (isRoot()) { // root's path is an empty string
            return "";
        }
        return directory.getAbsolutePath();
    }

    public void selectParent() {
        if (isRoot()) { // root does not have a parent directory
            return;
        }
        set(directory.getParentFile());
    }

    // See the repeating pattern?
    // if (isRoot())
    //     thenDo();
    // else
    //     otherwiseDo();

    public void selectPrevious() {
        String path = undoHistory.pop();
        redoHistory.push(path);
        setAndNotify(createFileFromStringPath(path));
    }

    public void selectNext() {
        String path = redoHistory.pop();
        undoHistory.push(path);
        setAndNotify(createFileFromStringPath(path));
    }

    // This is unrelated. Why is it here?
    private static File createFileFromStringPath(String url) {
        if (url.isEmpty()) {
            return null;
        }
        return new File(url);
    }
}
