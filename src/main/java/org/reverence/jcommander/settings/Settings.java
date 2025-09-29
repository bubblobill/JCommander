package org.reverence.jcommander.settings;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.reverence.jcommander.settings.Setting.TRANSPARENT;

public class Settings {
    private final File file;
    private final Properties properties = new Properties();
    private final List<SettingChangeListener> settingChangeListeners = new ArrayList<>();

    /**
     * Constructs a Settings object associated with the specified file.
     *
     * @param file the file where the settings are stored
     */
    public Settings(File file) {
        this.file = file;
        try {
            load();
        } catch (IOException e) {
            System.err.println(I18n.getText("error.settings.load") + ": " + e.getMessage());
        }
    }

    private void load() throws IOException {
        if (!file.exists()) {
            return;
        }
        try (InputStream is = new FileInputStream(file)) {
            properties.load(is);
        }
    }

    private void save() throws IOException {
        try (OutputStream os = new FileOutputStream(file)) {
            properties.store(os, "JCommander");
        }
    }

    /**
     * Sets the value for the specified option, saves the settings, and notifies listeners of the change.
     *
     * @param option the option whose value is being set
     * @param value  the new value for the option
     */
    public void set(Option option, Object value) {
        if(value instanceof Color color){
            value = color.getRGB();
        }
        String valueAsString = value.toString();
        properties.setProperty(option.toString(), valueAsString);

        try {
            save();
        } catch (IOException e) {
            System.err.println(I18n.getText("error.settings.save") + ": " + e.getMessage());
        }

        notifyAllSettingChanged(new SettingChangedEvent(option, valueAsString));
    }

    /**
     * Gets the value for the specified option, or its default value if not set.
     *
     * @param option the option to retrieve the value for
     * @return the value of the option
     */
    public String get(Option option) {
        return properties.getProperty(option.toString(), option.defaultValue.toString());
    }

    /**
     * Gets the value for the specified option, or its default value if not set.
     *
     * @param option the option to retrieve the value for
     * @return the value of the option
     */
    public Color getColour(Option option) {
        return new Color(Integer.parseInt(get(option)));
    }

    /**
     * Refreshes the settings, notifying listeners of any changes.
     */
    public void refreshSettings() {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Option option = Option.valueOf(entry.getKey().toString());
            notifyAllSettingChanged(new SettingChangedEvent(option, entry.getValue().toString()));
        }
    }

    private void notifyAllSettingChanged(SettingChangedEvent settingChangedEvent) {
        for (SettingChangeListener listener : settingChangeListeners) {
            listener.settingChanged(settingChangedEvent);
        }
    }

    /**
     * Adds a SettingChangeListener to be notified of changes to the settings.
     *
     * @param listener the SettingChangeListener to be added
     */
    public void addSettingChangedListener(SettingChangeListener listener) {
        settingChangeListeners.add(listener);
    }

    /**
     * Removes a SettingChangeListener from the list of listeners.
     *
     * @param listener the SettingChangeListener to be removed
     */
    public void removeSettingChangedListener(SettingChangeListener listener) {
        settingChangeListeners.remove(listener);
    }

    /**
     * Represents available options in the application settings.
     */
    public enum Option {
        ACTIVE_COLOR(Color.RED.getRGB()),
        PASSIVE_COLOR(Color.WHITE.getRGB()),
        SHOW_TREE_VIEW(true),
        HIGHLIGHT_ACTIVE_PANE(false),
        CELL_BORDER_COLOUR_SELECTED(Color.BLUE.getRGB()),
        CELL_BACKGROUND_COLOUR_SELECTED(Color.BLUE.getRGB()),
        CELL_FONT_COLOUR_SELECTED(Color.WHITE.getRGB()),
        CELL_BORDER_COLOUR(TRANSPARENT.getRGB()),
        CELL_BACKGROUND_COLOUR(Color.WHITE.getRGB()),
        CELL_FONT_COLOUR(Color.BLACK.getRGB()),
        ICON_SIZE(32),
        FONT_SIZE(13.5f)
        ;
        private final Object defaultValue;

        Option(Object defaultValue) {
            this.defaultValue = defaultValue;
        }
    }
}
