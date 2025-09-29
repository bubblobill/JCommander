package org.reverence.jcommander.settings;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
/**
 * Defines a listener interface for handling changes in application settings.
 */
public interface SettingChangeListener {

    /**
     * Invoked when a setting has changed.
     *
     * @param event the event representing the change in the setting
     */
    void settingChanged(SettingChangedEvent event);
}
