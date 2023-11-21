package hu.bme.jcommander;

import hu.bme.jcommander.pane.Controller;
import hu.bme.jcommander.pane.WorkPane;
import hu.bme.jcommander.pane.path.PathFieldController;
import hu.bme.jcommander.settings.Settings;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComponentFactoryTest {

    private FocusListener listener;
    private boolean settingsExisted;
    private File settingsFile;
    private Settings settings;
    private ComponentFactory factory;

    @BeforeAll
    public void createSettings() {
        settingsFile = new File("settings.txt");
        settings = new Settings(settingsFile);
    }

    @AfterAll
    public void deleteSettings() throws IOException {
        if (!settingsExisted) {
            Files.delete(settingsFile.toPath());
        }
    }

    @BeforeEach
    public void initFactory() {
        factory = new ComponentFactory(listener, settings);
    }

    @Test
    public void testCreation() {
        Controller controller = factory.create(PathFieldController.class, "hello");
        assertTrue(Arrays.stream(controller.component().getFocusListeners()).anyMatch(listener -> listener.equals(this.listener)));
    }
}
