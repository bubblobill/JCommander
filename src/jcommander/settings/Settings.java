package jcommander.settings;

import java.io.*;
import java.util.*;

public class Settings {

    public enum Option {
        SHOW_TREE_VIEW(true);

        private final Object defaultValue;

        Option(Object defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    private final File file;

    private final Properties properties = new Properties();

    private final List<SettingChangeListener> settingChangeListeners = new ArrayList<>();

    public Settings(File file) {
        this.file = file;

        try {
            load();
        } catch (IOException e) {
            System.err.println("Couldn't load settings: " + e.getMessage());
        }
    }

    public void load() throws IOException {
        if (!file.exists()) {
            return;
        }

        try (InputStream is = new FileInputStream(file)) {
            properties.load(is);
        }
    }

    public void save() throws IOException {
        try (OutputStream os = new FileOutputStream(file)) {
            properties.store(os, "JCommander");
        }
    }

    public void set(Option option, Object value) {
        properties.setProperty(option.toString(), value.toString());

        try {
            save();
        } catch (IOException e) {
            System.err.println("Couldn't save settings: " + e.getMessage());
        }

        notifyAllSettingChanged(new SettingChangedEvent(option, value));
    }

    public String get(Option option) {
        return properties.getProperty(option.toString(), option.defaultValue.toString());
    }

    public void refreshSettings() {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Option option = Option.valueOf(entry.getKey().toString());
            notifyAllSettingChanged(new SettingChangedEvent(option, entry.getValue()));
        }
    }

    private void notifyAllSettingChanged(SettingChangedEvent settingChangedEvent) {
        for (SettingChangeListener listener : settingChangeListeners) {
            listener.settingChanged(settingChangedEvent);
        }
    }

    public void addSettingChangedListener(SettingChangeListener listener) {
        settingChangeListeners.add(listener);
    }

    public void removeSettingChangedListener(SettingChangeListener listener) {
        settingChangeListeners.remove(listener);
    }
}
