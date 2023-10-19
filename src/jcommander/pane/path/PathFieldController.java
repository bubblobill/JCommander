package jcommander.pane.path;

import jcommander.filesystem.FileHandleBuilder;
import jcommander.pane.Controller;
import jcommander.pane.model.WorkingDirectory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PathFieldController implements Controller {

    private final WorkingDirectory wd;
    private final JTextField textField;

    public PathFieldController(WorkingDirectory wd) {
        this.wd = wd;
        textField = new JTextField(32);

        textField.addActionListener(e -> {
            String suggestedPath = textField.getText();
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

        textField.setFont(new Font("Sans Serif", Font.PLAIN, 20));
    }

    @Override
    public JComponent component() {
        return textField;
    }

    @Override
    public void refresh() {
        textField.setText(wd.getAbsolutePath());
    }
}
