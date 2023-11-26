package hu.bme.jcommander.operation;

import hu.bme.jcommander.pane.WorkPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class OperationExecutor implements Executor {

    // Automatically adjusts to the number of logical processors available on the target system.
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime()
            .availableProcessors());

    @Override
    public void execute(Runnable operation) {
        if (!(operation instanceof Operation)) {
            throw new IllegalArgumentException("Only operations can be executed.");
        }

        executor.execute(operation);
    }

    /**
     * Issues a new directory creation operation.
     *
     * @param activePane the active work pane
     */
    public void issueNewDirectoryOperation(WorkPane activePane) {
        Operation operation = new NewDirectoryOperation(
                Path.of(activePane.getWorkingDirectoryPath(), "New Directory"))
                .then(new RefreshOperation(activePane));
        executor.execute(operation);
    }

    /**
     * Issues a file deletion operation with user confirmation.
     *
     * @param activePane    the active work pane
     * @param parent        the parent component for displaying the confirmation dialog
     * @param selectedFiles the files to be deleted
     */
    public void issueDeleteOperation(WorkPane activePane, Component parent, Path[] selectedFiles) {
        String title = "Delete Files";
        String message = "Are you sure you want to delete every selected file and directory?";
        if (JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            executor.execute(new DeleteOperation(selectedFiles).then(new RefreshOperation(activePane)));
        }
    }

    /**
     * Issues a file operation between two work panes.
     *
     * @param activePane     the active work pane
     * @param passivePane    the passive work pane
     * @param operationClass the class of the file operation to be executed
     */
    public void issueFileOperation(WorkPane activePane, WorkPane passivePane, Class<? extends FileOperation> operationClass) {
        Constructor<?>[] declaredConstructors = operationClass.getDeclaredConstructors();
        Optional<Constructor<?>> matching = Arrays.stream(declaredConstructors)
                .filter(constructor -> constructor.getParameterCount() == 2)
                .findFirst();
        if (matching.isEmpty()) {
            throw new IllegalArgumentException("Operation class has no two-parameter constructor.");
        }
        Constructor<?> constructor = matching.get();

        for (File sourceFile : activePane.getSelectedFiles()) {
            File targetFile = new File(passivePane.getWorkingDirectoryPath(), sourceFile.getName());
            try {
                Operation operation = (Operation) constructor.newInstance(sourceFile.toPath(), targetFile.toPath());
                executor.execute(operation.then(new RefreshOperation(passivePane))
                        .then(new RefreshOperation(activePane)));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("Operation is unsuitable.");
            }
        }
    }
}
