package otus.hw04.gc;

class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 10_000_000;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            Object[] innerArray = new Object[local];
            for (int i = 0; i < local; i++) {
                innerArray[i] = new String(new char[57]);
            }
            Thread.sleep(5);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }
}
