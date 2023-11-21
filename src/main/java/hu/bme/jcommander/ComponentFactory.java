package hu.bme.jcommander;

import hu.bme.jcommander.pane.Controller;
import hu.bme.jcommander.settings.SettingChangeListener;
import hu.bme.jcommander.settings.Settings;

import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;

public class ComponentFactory {

    private final FocusListener listener;
    private final Settings settings;

    public ComponentFactory(FocusListener listener, Settings settings) {
        this.listener = listener;
        this.settings = settings;
    }

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
