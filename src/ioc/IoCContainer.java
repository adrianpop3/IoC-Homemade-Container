package ioc;

import annotations.Autowired;
import annotations.Component;
import annotations.Preferred;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IoCContainer {
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Class<?>> componentMappings = new HashMap<>();
    private final Map<String, Class<?>> customMappings = new HashMap<>();

    public IoCContainer(String packageName) {
        findAndRegisterComponents(packageName);
    }

    private void findAndRegisterComponents(String packageName) {
        List<Class<?>> classes = PackageScanner.findClasses(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {

                Component componentAnnotation = clazz.getAnnotation(Component.class);
                String customProp = componentAnnotation.customProp();
                if (!customProp.isEmpty()) {
                    customMappings.put(customProp, clazz);
                }

                componentMappings.put(clazz, clazz);

                for (Class<?> iface : clazz.getInterfaces()) {
                    if (componentMappings.containsKey(iface)) {
                        if (clazz.isAnnotationPresent(Preferred.class)) {
                            componentMappings.put(iface, clazz);
                        }
                    } else {
                        componentMappings.put(iface, clazz);
                    }
                }
            }
        }
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
                    constructor = implementationType.getConstructor();
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("No suitable constructor found for: " + implementationType.getName());
                }
            }

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Autowired autowired = getParameterAutowiredAnnotation(constructor, i);
                if (autowired != null && !autowired.implType().isEmpty()) {
                    String implType = autowired.implType();
                    parameters[i] = resolveByCustomProperty(parameterTypes[i], implType);
                } else {
                    parameters[i] = resolve(parameterTypes[i]);
                }
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

    private Autowired getParameterAutowiredAnnotation(Constructor<?> constructor, int index) {
        return constructor.getParameters()[index].getAnnotation(Autowired.class);
    }

    private <T> T resolveByCustomProperty(Class<T> componentType, String customProp) {
        System.out.println("Custom property: " + customProp);
        Class<?> implementationType = customMappings.get(customProp);
        if (implementationType == null) {
            throw new RuntimeException("No implementation found for customProp: " + customProp);
        }
        return resolve(implementationType.asSubclass(componentType));
    }
}
