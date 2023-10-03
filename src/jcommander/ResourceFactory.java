package jcommander;

import javax.swing.*;
import java.awt.*;

public class ResourceFactory {

    private static final int ICON_SIZE = 48;

    public static ImageIcon getSquareIcon(String filename) {
        Image image = new ImageIcon("images/" + filename).getImage();
        return new ImageIcon(image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
    }
}
