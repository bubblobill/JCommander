package org.reverence.jcommander;

/*
Code swiped from <a href="https://github.com/Zotyamester/JCommander">github.com/Zotyamester/JCommander</a> before I abused it. They get all the credit.
 */
import org.reverence.jcommander.pane.Controller;
import org.reverence.jcommander.settings.Setting;
import org.reverence.jcommander.settings.SettingChangeListener;
import org.reverence.jcommander.settings.Settings;

import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;

public class ComponentFactory {
    private final FocusListener listener;

    /**
     * Initializes a ComponentFactory responsible for the creation of Controller's subclasses.
     *
     * @param listener a listener to invoke from the Controller's component, when it gains or loses focus
     */
    public ComponentFactory(FocusListener listener) {
        this.listener = listener;
    }

    /**
     * Creates a concrete Controller with a preset listener hook for focus events and a bound to a settings instance
     * (if it supports reacting to SettingChangedEvents).
     *
     * @param type a Class type representing the type of Controller to be created
     * @param args arguments passed to the yet to be created Controller's constructor
     * @param <T>  the type of Controller to be created (it will always be inferred, no need to write it out explicitly)
     * @return an instance of a concrete Controller if the creation succeeded, null otherwise
     */
    public <T extends Controller> T create(Class<T> type, Object... args) {
        try {
            Class<?>[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }

            Constructor<T> constructor = type.getConstructor(parameterTypes);

            T instance = constructor.newInstance(args);
            instance.component().addFocusListener(listener);

            if (instance instanceof SettingChangeListener settingChangeListener) {
                Setting.SETTINGS.addSettingChangedListener(settingChangeListener);
            }

            return instance;
        } catch (Exception e) {
            return null;
        }
    }
}
