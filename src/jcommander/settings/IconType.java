package jcommander.settings;

public enum IconType {
    COMPUTER, COPY, DIRECTORY, DISK, FILE, FIND, LEFT, MOVE, NEW_DIRECTORY, REFRESH, RIGHT, UP;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
