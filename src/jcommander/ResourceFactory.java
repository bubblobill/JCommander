package jcommander;

import javax.swing.*;
import java.awt.*;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class ResourceFactory {

    private static final Map<String, SoftReference<Icon>> cache = new HashMap<>();

    private static final int ICON_SIZE = 48;

    public static Icon getIcon(String filename) {
        SoftReference<Icon> iconRef;
        Icon icon;
        if ((iconRef = cache.get(filename)) != null && (icon = iconRef.get()) != null) {
            return icon;
        }

        Image image = new ImageIcon("images/" + filename).getImage();
        icon = new ImageIcon(image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
        iconRef = new SoftReference<>(icon);
        cache.put(filename, iconRef);
        return icon;
    }
}
