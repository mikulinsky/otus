package otus.hw05.autoLog.demo;

import otus.hw05.autoLog.annotations.Log;

public class DemoClass implements DemoClassInterface {

    @Log
    public void testMethod1(){

    }

    @Log
    public String testMethod2(String str) {
        return str;
    }

    @Log
    public int testMethod3(int num1, int num2) {
        return num1 + num2;
    }
}
