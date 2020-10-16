package otus.hw02.generics;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private int size;
    private Object[] data;
    private boolean isFixedSize = false;

    public DIYArrayList() {
        this.size = 0;
        this.data = new Object[0];
    }

    public DIYArrayList(int size) {
        if (size >= 0) {
            this.size = 0;
            this.data = new Object[size];
            this.isFixedSize = true;
        } else {
            throw new IllegalArgumentException("init size < 0");
        }
    }

    @Override
    public int size() {
        return this.data.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        if (this.size == this.data.length) {
            if (this.isFixedSize)
                throw new OutOfMemoryError();
            this.data = this.updateCapacity(this.size + 1);
        }
        this.data[this.size] = e;
        this.size += 1;
        return true;
    }

    @Override
    public E get(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("get index < 0");
        if (index > this.size())
            throw new IndexOutOfBoundsException("set index > size");

        return (E) this.data[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0)
            throw new IndexOutOfBoundsException("set index < 0");
        if (index > this.size())
            throw new IndexOutOfBoundsException("set index > size");
        if (index > this.size)
            this.size = index;

        return (E) (this.data[index] = element);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.data, this.size);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomIterator();
    }

    private Object[] updateCapacity(long newCapacity) {
        if (newCapacity < 0)
            throw new OutOfMemoryError("updateCapacity to size < 0");
        if (newCapacity > (long)Integer.MAX_VALUE)
            throw new OutOfMemoryError("updateCapacity to size > Integer.MAX_VALUE");

        return Arrays.copyOf(this.data, (int)newCapacity);
    }

    public class CustomIterator implements ListIterator<E> {
        private int nextIndex;
        private int current;

        public CustomIterator() {
            nextIndex = 0;
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return this.nextIndex < DIYArrayList.this.size;
        }

        @Override
        public E next() {
            this.current = this.nextIndex;
            return DIYArrayList.this.get(this.nextIndex++);
        }

        @Override
        public void set(E e) {
            DIYArrayList.this.set(this.current, e);
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException("iterator_hasPrevious");
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException("iterator_previous");
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("iterator_nextIndex");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("iterator_previousIndex");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("iterator_remove");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("iterator_add");
        }
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("contains");
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("iterator");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("toArray(T[] a)");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("containsAll");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("addAll(Collection<? extends E> c)");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("addAll(int index, Collection<? extends E> c)");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear");
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("add(int index, E element)");
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("indexOf");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("lastIndexOf");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("listIterator(int index)");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList");
    }
}