package org.reverence.jcommander;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.settings.IconType;

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
        // Prevent instantiation from outside the class.
    }

    /**
     * Creates an icon of the given type.
     *
     * <p>
     * As an optimization, it might not instantiate a new Icon, but instead will try to reuse a previously loaded
     * icon of the same type. This mechanism can be thought of as caching or as an example of the Flyweight Pattern.
     * </p>
     *
     * @param type the type of icon
     * @return an icon of the given type
     */
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
