package otus.hw03.annotations;

import otus.hw03.annotations.testFramework.TestLauncher;
import otus.hw03.annotations.testFramework.models.TestResults;

public class Main {
    public static void main(String[] args) {
        TestLauncher testLauncher = new TestLauncher();
        TestResults results;

        try {
            results = testLauncher.launch("otus.hw03.annotations.tests.DIYArrayListTest");
        } catch (Exception e) {
            results = new TestResults(0, 0);
        }

        System.out.println("Test passed: " + results.isPassed());
        System.out.println("Tests: " + results.getAll() +
                        ", Passed: " + results.getPassed() +
                        ", Failed: " + results.getFailed());
    }
}