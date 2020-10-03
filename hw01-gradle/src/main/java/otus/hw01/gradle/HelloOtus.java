package otus.hw01.gradle;

import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3);

        for (Integer item: list) {
            System.out.println(item);
        }
    }

}
