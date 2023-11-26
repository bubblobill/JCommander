package hu.bme.jcommander.pane.path;

import hu.bme.jcommander.ResourceFactory;
import hu.bme.jcommander.pane.Controller;
import hu.bme.jcommander.pane.model.WorkingDirectory;
import hu.bme.jcommander.settings.IconType;

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
