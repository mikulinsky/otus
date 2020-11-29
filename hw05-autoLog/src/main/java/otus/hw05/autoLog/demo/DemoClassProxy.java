package otus.hw05.autoLog.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.hw05.autoLog.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DemoClassProxy {

    public static DemoClassInterface createProxiedDemoClass() {
        InvocationHandler handler = new DemoInvocationHandler(new DemoClass());
        return (DemoClassInterface) Proxy.newProxyInstance(DemoClassProxy.class.getClassLoader(),
                new Class<?>[]{DemoClassInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {

        private final Object myClass;
        private final List<String> methodsToLog;
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        DemoInvocationHandler(Object myClass)
        {
            this.myClass = myClass;
            this.methodsToLog = new ArrayList<>();

            Class<?> clazz = myClass.getClass();
            Method[] methods = clazz.getMethods();

            for (Method method : methods)  {
                if (method.isAnnotationPresent(Log.class)) {
                    methodsToLog.add(getMethodNameWithParams(method));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            for (String methodToLog : methodsToLog) {
                if (getMethodNameWithParams(method).equals(methodToLog)) {
                    logger.info(getLogMessage(method.getName(), args));
                    break;
                }
            }
            return method.invoke(myClass, args);
        }

        private String getMethodNameWithParams(Method method){
            return method.getName() + "("
                    + Arrays.stream(method.getParameterTypes())
                                .map(Class::toString)
                                .collect(Collectors.joining(","))
                    + ")";
        }

        public String getLogMessage(String methodName, Object[] args) {

            String logStr = "executed method: " + methodName + " with params: ";
            String strArgs = "";

            if (args != null) {
                strArgs = Arrays.stream(args)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
            }
            return logStr + strArgs;
        }
    }
}
