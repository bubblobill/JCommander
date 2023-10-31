package jcommander.settings;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class Property {

    protected final List<ChangeListener> listeners = new ArrayList<>();

    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }

    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }

    public abstract String key();

    public abstract String value();
}
