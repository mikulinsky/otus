package otus.hw03.annotations.testFramework;

import otus.hw03.annotations.testFramework.annotations.After;
import otus.hw03.annotations.testFramework.annotations.Before;
import otus.hw03.annotations.testFramework.annotations.Test;
import otus.hw03.annotations.testFramework.models.TestResults;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TestLauncher {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public TestResults launch(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.warning("Get class: " + e.getCause());
            return new TestResults(0, 0);
        }
        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            logger.warning("Get constructor: " + e.getCause());
            return new TestResults(0, 0);
        }
        Method[] methods = clazz.getMethods();

        ArrayList<Method> beforeMethods = new ArrayList<>();
        ArrayList<Method> testMethods = new ArrayList<>();
        ArrayList<Method> afterMethods = new ArrayList<>();
        int failCount = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class))
                beforeMethods.add(method);
            if (method.isAnnotationPresent(Test.class))
                testMethods.add(method);
            if (method.isAnnotationPresent(After.class))
                afterMethods.add(method);
        }

        for (Method method : testMethods) {
            Object instance;
            try {
                instance = constructor.newInstance();
            } catch (Exception e) {
                logger.warning("Get instance: " + e.getCause());
                return new TestResults(0, 0);
            }
            for (Method beforeMethod : beforeMethods) {
                try {
                    beforeMethod.invoke(instance);
                } catch (Exception e) {
                    logger.warning("BeforeMethod " + beforeMethod.getName() + " ended with exception: " + e.getCause());
                }
            }
            try {
                method.invoke(instance);
                logger.info("Test " + method.getName() + " passed");
            } catch (Exception e) {
                logger.warning("Test " + method.getName() + " failed");
                failCount++;
            }
            for (Method afterMethod : afterMethods) {
                try {
                    afterMethod.invoke(instance);
                } catch (Exception e) {
                    logger.warning("BeforeMethod " + afterMethod.getName() + " ended with exception: " + e.getCause());
                }
            }
        }

        return new TestResults(testMethods.size(), failCount);
    }
}
