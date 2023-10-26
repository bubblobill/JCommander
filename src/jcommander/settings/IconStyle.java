package jcommander.settings;

public enum IconStyle {
    COLORFUL("colorful"), OLD_SCH("old-sch");

    private final String name;

    IconStyle(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
