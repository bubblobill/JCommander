package hu.bme.jcommander.settings;

public enum IconType {
    COMPUTER, COPY, DIRECTORY, DISK, FILE, LEFT, MOVE, NEW_DIRECTORY, DELETE, REFRESH, RIGHT, UP;

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
}
