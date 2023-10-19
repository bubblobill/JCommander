package jcommander.pane.directorylist;

import jcommander.filesystem.Handle;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class DirectoryListModel implements ListModel<Handle> {

    private final List<ListDataListener> listeners = new ArrayList<>();
    private Handle[] files = new Handle[0];

    public void listDirectory(Handle[] files) {
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
    public Handle getElementAt(int index) {
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
