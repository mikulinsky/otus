package otus.hw08.json;

import java.util.Collection;

public class TestObject {
    private final Integer field1;
    private final String field2;
    private final Collection<String> field3;

    TestObject(Integer field1, String field2, Collection<String> field3){
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TestObject testObject = (TestObject) obj;
        return field1.equals(testObject.field1) &&
                field2.equals(testObject.field2) &&
                field3.containsAll(testObject.field3) &&
                testObject.field3.containsAll(field3);
    }
}
