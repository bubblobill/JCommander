package hu.bme.jcommander.settings;

/**
 * Represents an event indicating a change in a setting.
 *
 * @param option the option whose value changed
 * @param value  the new value of the option
 */
public record SettingChangedEvent(Settings.Option option, String value) {

}
