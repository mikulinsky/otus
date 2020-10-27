package otus.hw03.annotations.tests;

import otus.hw02.generics.DIYArrayList;
import otus.hw03.annotations.testFramework.annotations.Before;
import otus.hw03.annotations.testFramework.annotations.Test;

import java.util.Collections;
import java.util.Random;

public class DIYArrayListTest {

    private DIYArrayList<Integer> testList;

    public DIYArrayListTest() {
        testList = new DIYArrayList<Integer>();
    }

    @Before
    public void fillingList() {
        Integer[] testCollection = new Integer[50];

        for (int i = 0; i < 50; i++)
            testCollection[i] = new Random().nextInt();

        Collections.addAll(testList, testCollection);
    }

    @Test
    public void addElement() {
        testList.add(new Random().nextInt());
    }

    @Test
    public void getSize() {
        testList.size();
    }

    @Test
    public void getElement() {
        Integer index = new Random().nextInt(testList.size());
        testList.get(index);
    }

    @Test
    public void setElement() {
        Integer index = new Random().nextInt(testList.size());
        testList.set(index, new Random().nextInt());
    }

    @Test
    public void randomException() {
        if (new Random().nextInt() % 7 == 0) {
            throw new RuntimeException();
        }
    }
}
