package org.reverence.jcommander.settings;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
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
