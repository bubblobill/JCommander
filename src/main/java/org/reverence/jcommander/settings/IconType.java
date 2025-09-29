package org.reverence.jcommander.settings;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
/**
 * Represents a type of icon that is supported in the application.
 */
public enum IconType {
    COMPUTER("clock.svg", null),
    COPY(null, null),
    DIRECTORY(null, null),
    DISK(null, null),
    FILE(null, null),
    LEFT(null, null),
    MOVE(null, null),
    NEW_DIRECTORY(null, null),
    DELETE(null, null),
    REFRESH(null, null),
    RIGHT(null, null),
    UP(null, null);
    private final String fileName;
    private final String fallbackFileName;
    IconType(String fileName, String fallbackFileName){
        this.fileName = fileName;
        this.fallbackFileName = fileName;
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
    public String getFallbackFileName() {
        if(fallbackFileName != null) {
            return fallbackFileName;
        }
        return this + ".png";
    }
    public String getFileName() {
       if(fileName != null) {
           return fileName;
       }
       return this + ".png";
    }
}
