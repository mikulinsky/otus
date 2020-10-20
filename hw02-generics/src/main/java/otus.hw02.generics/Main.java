package otus.hw02.generics;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        DIYArrayList<Integer> arrayList1 = new DIYArrayList<>(50);
        DIYArrayList<Integer> arrayList2 = new DIYArrayList<>(50);

        Integer[] testCollection = new Integer[50];

        for (int i = 0; i < 50; i++)
            testCollection[i] = new Random().nextInt();

        Collections.addAll(arrayList1, testCollection);
        System.out.println("arrayList.size = " + arrayList1.size());
        System.out.println("arrayList2.size = " + arrayList2.size());
        Collections.copy(arrayList2, arrayList1);
        Collections.sort(arrayList2);

        System.out.println("arrayList1 = " + Arrays.toString(arrayList1.toArray()));
        System.out.println("arrayList2 = " + Arrays.toString(arrayList2.toArray()));
    }
}