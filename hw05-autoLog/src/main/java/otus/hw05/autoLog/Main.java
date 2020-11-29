package otus.hw05.autoLog;

import otus.hw05.autoLog.demo.DemoClassInterface;
import otus.hw05.autoLog.demo.DemoClassProxy;

public class Main {
    public static void main(String[] args) {
        DemoClassInterface demo = DemoClassProxy.createProxiedDemoClass();

        demo.testMethod1();
        demo.testMethod2("asdf");
        demo.testMethod3(1, 2);
    }
}