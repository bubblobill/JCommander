package hu.bme.jcommander;

import hu.bme.jcommander.settings.IconType;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ResourceFactory {

    private static final Map<IconType, SoftReference<Icon>> cache = new EnumMap<>(IconType.class);
    private static final int ICON_SIZE = 48;

    private ResourceFactory() {
        // So that it cannot be instantiated from outside.
    }

    public static Icon getIcon(IconType type) {
        SoftReference<Icon> iconRef;
        Icon icon;
        if ((iconRef = cache.get(type)) != null && (icon = iconRef.get()) != null) {
            return icon;
        }

        String filename = "images" + File.separator + type + ".png";
        URL resource = ResourceFactory.class.getClassLoader().getResource(filename);
        Image image = new ImageIcon(Objects.requireNonNull(resource)).getImage();
        icon = new ImageIcon(image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
        iconRef = new SoftReference<>(icon);
        cache.put(type, iconRef);
        return icon;
    }
}
