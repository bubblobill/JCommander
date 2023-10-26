package jcommander.settings;

public enum IconType {
    COMPUTER("computer"), COPY("copy"), DIRECTORY("directory"), DISK("disk"), FILE("file"),
    FIND("find"), LEFT("left"), MOVE("move"), NEW_DIRECTORY("new_directory"), REFRESH("refresh"),
    RIGHT("right"), UP("up");

    private final String name;

    IconType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
