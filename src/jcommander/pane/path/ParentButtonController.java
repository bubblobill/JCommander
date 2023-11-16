package jcommander.pane.path;

import jcommander.ResourceFactory;
import jcommander.pane.Controller;
import jcommander.pane.model.WorkingDirectory;
import jcommander.settings.IconType;

import javax.swing.*;

public class ParentButtonController implements Controller {

    private final WorkingDirectory wd;
    private final JButton button;

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
