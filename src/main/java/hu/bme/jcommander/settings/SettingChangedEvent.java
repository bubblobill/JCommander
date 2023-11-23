package hu.bme.jcommander.settings;

public record SettingChangedEvent(Settings.Option option, String value) {

}
