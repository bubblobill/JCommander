package jcommander.settings;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private final Properties properties = new Properties();

    public Settings(File file) throws IOException {
        //properties.load(new FileInputStream(file));
    }

    public void setShowTreeView(boolean selected) {
        // TODO
    }
}
