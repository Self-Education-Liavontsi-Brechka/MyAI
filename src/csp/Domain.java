package csp;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Domain<VAL> implements Iterable<VAL> {
    private final VAL[] values;

    @SuppressWarnings("unchecked")
    public Domain(List<VAL> values) {
        this.values = (VAL[]) values.toArray();
    }

    @SafeVarargs
    public Domain(VAL... values) {
        this.values = values;
    }

    public int size() {
        return values.length;
    }

    public VAL get(int index) {
        return values[index];
    }

    public boolean isEmpty() {
        return values.length == 0;
    }

    public boolean contains(VAL value) {
        for (VAL val : values) if (val.equals(value)) return true;
        return false;
    }

    @Override
    public Iterator<VAL> iterator() {
        return new Iterator<VAL>() {
            int counter;

            @Override
            public boolean hasNext() {
                return counter < values.length;
            }

            @Override
            public VAL next() {
                return values[counter++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domain<?> domain = (Domain<?>) o;

        return Arrays.equals(values, domain.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        return "Domain{" + Arrays.toString(values) + '}';
    }
}
