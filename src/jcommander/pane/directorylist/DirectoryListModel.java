package jcommander.pane.directorylist;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryListModel implements ListModel<File> {

    File directory;

    File[] listOfFiles;
    private final List<ListDataListener> listeners = new ArrayList<>();

    public DirectoryListModel(File directory) {
        if (!directory.isDirectory()) {
            throw new InvalidParameterException("Parameter must be a directory");
        }

        this.directory = directory;
        refreshDirectoryContent();
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
        refreshDirectoryContent();
    }

    public void refreshDirectoryContent() {
        listOfFiles = directory.listFiles();
        if (listOfFiles == null) {
            listOfFiles = new File[0];
        }

        notifyAllDirectoryChanged();
    }

    private void notifyAllDirectoryChanged() {
        ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED,
                0, listOfFiles.length);
        for (ListDataListener listener : listeners) {
                listener.contentsChanged(e);
        }
    }

    @Override
    public int getSize() {
        return listOfFiles.length;
    }

    @Override
    public File getElementAt(int index) {
        return listOfFiles[index];
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }
}
