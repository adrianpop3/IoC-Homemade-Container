package ioc;

import annotations.Autowired;
import annotations.Component;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IoCContainer {
    private Map<Class<?>, Object> singletons = new HashMap<>();

    public IoCContainer(String packageName) {
        registerComponents(packageName);
    }

    private void registerComponents(String packageName) {
        List<Class<?>> classes = PackageScanner.findClasses(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                resolve(clazz);
            }
        }
    }

    public <T> T resolve(Class<T> componentType) {
        if (singletons.containsKey(componentType)) {
            return componentType.cast(singletons.get(componentType));
        }

        try {
            Constructor<?> constructor = findAutowiredConstructor(componentType);
            if (constructor == null) {
                throw new RuntimeException("No @Autowired constructor found for: " + componentType);
            }

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                parameters[i] = resolve(parameterTypes[i]);
            }

            T instance = componentType.cast(constructor.newInstance(parameters));
            singletons.put(componentType, instance);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve component: " + componentType.getName(), e);
        }
    }

    private Constructor<?> findAutowiredConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                return constructor;
            }
        }
        return null;
    }
}