package org.reverence.jcommander.settings;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
/**
 * An abstract class that provides a default, blank implementation of the {@link SettingChangeListener} interface.
 * Subclasses can extend this class and override specific methods to handle setting change events.
 */
public abstract class SettingChangeAdapter implements SettingChangeListener {

    /**
     * A default implementation that does nothing.
     *
     * @param event the event representing the change in the setting
     */
    @Override
    public void settingChanged(SettingChangedEvent event) {
        // Blank.
    }
}
