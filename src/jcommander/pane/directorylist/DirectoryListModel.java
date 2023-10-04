package jcommander.pane.directorylist;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryListModel implements ListModel<File> {

    // null-check avoidance + optimization technique:
    //  - in order to avoid null-checks in @see getSize() and @see getElementAt(),
    //    we maintain a steady, never-null list for the current directory's content;
    //    thus even if for a given directory we can't list out its contents, we
    //    propagate an empty collection of items instead of admitting the nullity
    //  - a zero-length array can be shared across multiple instances, as there's
    //    nothing to be shared there, except for the final *length* field;
    //    therefore we can hold onto only one concrete zero-length array all-along
    //    which should result in less allocation
    private static final File[] defaultZeroList = new File[0];

    File[] listOfFiles = DirectoryListModel.defaultZeroList;
    private final List<ListDataListener> listeners = new ArrayList<>();


    public void listDirectory(File directory) {
        if (!directory.isDirectory()) {
            throw new InvalidParameterException("Parameter must be a directory");
        }

        listOfFiles = directory.listFiles();
        if (listOfFiles == null) {
            listOfFiles = defaultZeroList;
        }

        notifyAllDirectoryChanged();
    }

    private void notifyAllDirectoryChanged() {
        ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, listOfFiles.length);
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
