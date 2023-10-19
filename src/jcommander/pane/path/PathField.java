package jcommander.pane.path;

import jcommander.filesystem.FileHandleBuilder;
import jcommander.pane.Refreshable;
import jcommander.pane.model.WorkingDirectory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PathField extends JTextField implements Refreshable {

    private final WorkingDirectory wd;

    public PathField(WorkingDirectory wd) {
        super(32);
        this.wd = wd;

        addActionListener(e -> {
            String suggestedPath = getText();
            if (suggestedPath.isBlank()) {
                wd.resetToRoot();
            } else {
                File validatorFile = new File(suggestedPath);
                if (validatorFile.exists() && validatorFile.isDirectory()) {
                    wd.setTo(new FileHandleBuilder(validatorFile).toFileHandle());
                } else {
                    refresh();
                }
            }
        });

        setFont(new Font("Sans Serif", Font.PLAIN, 20));
    }

    @Override
    public void refresh() {
        setText(wd.getAbsolutePath());
    }
}
