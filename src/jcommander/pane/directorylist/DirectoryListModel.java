package jcommander.pane.directorylist;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryListModel implements ListModel<File> {

    private File[] files = new File[0];
    private final List<ListDataListener> listeners = new ArrayList<>();

    public void listDirectory(File[] files) {
        this.files = files;
        notifyAllContentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, files.length));
    }

    private void notifyAllContentsChanged(ListDataEvent e) {
        for (ListDataListener listener : listeners) {
            listener.contentsChanged(e);
        }
    }

    @Override
    public int getSize() {
        return files.length;
    }

    @Override
    public File getElementAt(int index) {
        return files[index];
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
