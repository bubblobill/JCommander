package org.reverence.jcommander;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import com.bw.jtools.ui.OpenIdeSVGLoader;
import com.bw.jtools.ui.ShapeIcon;
import org.reverence.jcommander.settings.IconType;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import static org.reverence.jcommander.settings.Setting.*;

public class ResourceFactory {
    private static final OpenIdeSVGLoader svgLoader = new OpenIdeSVGLoader();
    private static final Map<IconType, SoftReference<Icon>> cache = new EnumMap<>(IconType.class);

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

        String filename = type.getFileName();
        String resourcePath = MessageFormat.format("images{0}{1}", File.separator, filename);
        URL resource = ResourceFactory.class.getClassLoader().getResource(resourcePath);
        if(filename.toLowerCase().endsWith(".svg")){
            try {
                icon = svgLoader.loadIcon(resource);
                double scale = (ICON_SIZE) / Math.max(((ShapeIcon) icon).getIconHeight2D(), ((ShapeIcon) icon).getIconWidth2D());
                ((ShapeIcon)icon).setScale(scale, scale);
                ((ShapeIcon)icon).setInlineBorder(true, TRANSPARENT);

            } catch (IOException e) {
                resource = ResourceFactory.class.getClassLoader().getResource(resourcePath.replace(filename, type.getFallbackFileName()));
                icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(resource)).getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
            }
        } else {
            Image image = new ImageIcon(Objects.requireNonNull(resource)).getImage();
            icon = new ImageIcon(image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
        }
        iconRef = new SoftReference<>(icon);
        cache.put(type, iconRef);
        return icon;
    }
}
