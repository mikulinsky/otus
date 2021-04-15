package otus.hw13.appcontainer;

import otus.hw13.appcontainer.api.AppComponent;
import otus.hw13.appcontainer.api.AppComponentsContainer;
import otus.hw13.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...

        Object configClassInstance;
        try {
            configClassInstance = configClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        List<Method> beanClasses = Arrays.asList(configClass.getDeclaredMethods());
        beanClasses.sort(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()));
        for (var beanClass : beanClasses) {
            try {
                Object bean = beanClass.invoke(
                        configClassInstance,
                        Arrays.stream(beanClass.getParameters())
                                .map(Parameter::getType)
                                .map(this::getAppComponent)
                                .toArray());
                appComponents.add(bean);
                appComponentsByName.put(beanClass.getAnnotation(AppComponent.class).name(), bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream().filter(componentClass::isInstance).findFirst().orElseThrow();
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
