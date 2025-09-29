package org.reverence.jcommander.settings;

import java.awt.*;
import java.io.File;

public class Setting {
    public static final Color TRANSPARENT = new Color(0.5f, 0.5f, 0.5f, 0);
    private static final String SETTINGS_FILE_NAME = "settings.txt";
    public static final String INSTANCE_TITLE;
    public static final Settings SETTINGS;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final Color ACTIVE_COLOR;
    public static final Color PASSIVE_COLOR;
    public static final float FONT_SIZE;
    public static final int ICON_SIZE;
    static {
        INSTANCE_TITLE = I18n.getText("frame.name");
        SETTINGS = new Settings(new File(SETTINGS_FILE_NAME));
        ACTIVE_COLOR = Color.decode(SETTINGS.get(Settings.Option.ACTIVE_COLOR));
        PASSIVE_COLOR = Color.decode(SETTINGS.get(Settings.Option.PASSIVE_COLOR));
        FONT_SIZE = Float.parseFloat(SETTINGS.get(Settings.Option.FONT_SIZE));
        ICON_SIZE = Integer.parseInt(SETTINGS.get(Settings.Option.ICON_SIZE));
    }
}
