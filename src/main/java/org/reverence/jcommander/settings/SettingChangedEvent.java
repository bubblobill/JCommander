package org.reverence.jcommander.settings;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
/**
 * Represents an event indicating a change in a setting.
 *
 * @param option the option whose value changed
 * @param value  the new value of the option
 */
public record SettingChangedEvent(Settings.Option option, String value) {

}
