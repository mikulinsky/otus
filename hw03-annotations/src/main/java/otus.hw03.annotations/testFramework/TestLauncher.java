package otus.hw03.annotations.testFramework;

import otus.hw03.annotations.testFramework.annotations.After;
import otus.hw03.annotations.testFramework.annotations.Before;
import otus.hw03.annotations.testFramework.annotations.Test;
import otus.hw03.annotations.testFramework.models.TestResults;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestLauncher {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public TestResults launch(String className) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();
        Method[] methods = clazz.getMethods();

        List<Method> beforeMethods = this.getMethodsWithAnnotation(methods, Before.class);
        List<Method> testMethods = this.getMethodsWithAnnotation(methods, Test.class);
        List<Method> afterMethods = this.getMethodsWithAnnotation(methods, After.class);
        int failCount = 0;

        for (Method method : testMethods) {
            Object instance = constructor.newInstance();
            try {
                this.runMethods(beforeMethods, instance);
                method.invoke(instance);
                logger.info("Test " + method.getName() + " passed");
            } catch (Exception e) {
                logger.warning("Test " + method.getName() + " failed");
                logger.warning(e.getCause().toString());
                failCount++;
            }
            for (Method afterMethod : afterMethods) {
                try {
                    afterMethod.invoke(instance);
                } catch (Exception e) {
                    logger.warning(e.getCause().toString());
                }
            }
        }

        return new TestResults(testMethods.size(), failCount);
    }

    private List<Method> getMethodsWithAnnotation(Method[] methods, Class<? extends Annotation> clazz) {
        ArrayList<Method> filteredMethods = new ArrayList<>();

        for(Method method : methods) {
            if (method.isAnnotationPresent(clazz))
                filteredMethods.add(method);
        }

        return filteredMethods;
    }

    private void runMethods(List<Method> methods, Object instance)
            throws InvocationTargetException, IllegalAccessException {

        for (Method method : methods)
            method.invoke(instance);

    }
}
