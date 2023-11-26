package hu.bme.jcommander.settings;

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
