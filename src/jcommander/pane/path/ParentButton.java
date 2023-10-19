package jcommander.pane.path;

import jcommander.ResourceFactory;
import jcommander.pane.Refreshable;
import jcommander.pane.model.WorkingDirectory;

import javax.swing.*;

public class ParentButton extends JButton implements Refreshable {

    private final WorkingDirectory wd;

    public ParentButton(WorkingDirectory wd) {
        super(ResourceFactory.getIcon("up.png"));
        this.wd = wd;
        addActionListener(e -> wd.selectParent());
    }

    @Override
    public void refresh() {
        setEnabled(!wd.isRoot());
    }
}
