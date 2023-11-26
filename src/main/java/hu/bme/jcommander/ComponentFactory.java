package hu.bme.jcommander;

import hu.bme.jcommander.pane.Controller;
import hu.bme.jcommander.settings.SettingChangeListener;
import hu.bme.jcommander.settings.Settings;

import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;

public class ComponentFactory {

    private final FocusListener listener;
    private final Settings settings;

    /**
     * Initializes a ComponentFactory responsible for the creation of Controller's subclasses.
     *
     * @param listener a listener to invoke from the Controller's component, when it gains or loses focus
     * @param settings a setting to be passed to the instantiated Controllers' components
     */
    public ComponentFactory(FocusListener listener, Settings settings) {
        this.listener = listener;
        this.settings = settings;
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
                settings.addSettingChangedListener(settingChangeListener);
            }

            return instance;
        } catch (Exception e) {
            return null;
        }
    }
}
