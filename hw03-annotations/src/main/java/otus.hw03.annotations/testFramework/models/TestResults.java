package otus.hw03.annotations.testFramework.models;

public class TestResults {
    private final Integer all;
    private final Integer failed;

    public TestResults(Integer all, Integer failed) {
        if (all < failed)
            throw new IllegalArgumentException();
        this.all = all;
        this.failed = failed;
    }

    public boolean isPassed() {
        return this.failed == 0;
    }

    public Integer getAll() {
        return this.all;
    }

    public Integer getFailed() {
        return this.failed;
    }

    public Integer getPassed() {
        return this.all - this.failed;
    }
}
