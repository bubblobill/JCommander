package org.reverence.jcommander.pane.path;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.ResourceFactory;
import org.reverence.jcommander.pane.Controller;
import org.reverence.jcommander.pane.model.WorkingDirectory;
import org.reverence.jcommander.settings.IconType;

import javax.swing.*;

public class ParentButtonController implements Controller {

    private final WorkingDirectory wd;
    private final JButton button;

    /**
     * Constructs a ParentButtonController with the specified {@code WorkingDirectory}.
     *
     * @param wd the working directory to be managed by this controller
     */
    public ParentButtonController(WorkingDirectory wd) {
        this.wd = wd;

        button = new JButton(ResourceFactory.getIcon(IconType.UP));
        button.addActionListener(e -> wd.selectParent());
    }

    @Override
    public JComponent component() {
        return button;
    }

    @Override
    public void refresh() {
        button.setEnabled(!wd.isRoot());
    }
}
