package ioc;

import annotations.Autowired;
import annotations.Component;
import annotations.Primary;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IoCContainer {
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Class<?>> componentMappings = new HashMap<>();

    public IoCContainer(String packageName) {
        findAndRegisterComponents(packageName);
    }

    private void findAndRegisterComponents(String packageName) {
        List<Class<?>> classes = PackageScanner.findClasses(packageName);

        // Identify all components and register implementations for interfaces
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                componentMappings.put(clazz, clazz);  // Register the class as its own implementation

                // Register interfaces to implementation mappings
                for (Class<?> iface : clazz.getInterfaces()) {
                    if (componentMappings.containsKey(iface)) {
                        // Prefer the @Primary implementation if multiple implementations exist
                        if (clazz.isAnnotationPresent(Primary.class)) {
                            componentMappings.put(iface, clazz);
                        }
                    } else {
                        componentMappings.put(iface, clazz);
                    }
                }
            }
        }

//        // Eagerly instantiate and cache singletons
//        for (Class<?> clazz : componentMappings.keySet()) {
//            if (!singletons.containsKey(clazz)) {
//                resolve(clazz);
//            }
//        }
    }

    public <T> T resolve(Class<T> componentType) {
        if (singletons.containsKey(componentType)) {
            return componentType.cast(singletons.get(componentType));
        }

        Class<?> implementationType = componentMappings.get(componentType);
        if (implementationType == null) {
            throw new RuntimeException("No implementation found for: " + componentType.getName());
        }

        try {
            Constructor<?> constructor = findAutowiredConstructor(implementationType);
            if (constructor == null) {
                try {
                    constructor = implementationType.getConstructor();  // No-arg constructor
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("No suitable constructor found for: " + implementationType.getName());
                }
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
