package hu.bme.jcommander.settings;

/**
 * Represents a type of icon that is supported in the application.
 */
public enum IconType {
    COMPUTER, COPY, DIRECTORY, DISK, FILE, LEFT, MOVE, NEW_DIRECTORY, DELETE, REFRESH, RIGHT, UP;

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
}
