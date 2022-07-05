package expression.with_generics;

public class ConstWithGenerics<T> implements MultyExpressionWithGenerics<T> {
    private final T integer;

    public ConstWithGenerics(T integer) {
        this.integer = integer;
    }

    @Override
    public String toString() {
        return integer + "";
    }

    @Override
    public int evaluate(int x) {
        return (int) integer;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) integer;
    }

    @Override
    public T evaluate(T x) {
        return integer;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return integer;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ConstWithGenerics<?>) {
            return integer == ((ConstWithGenerics<?>) o).integer;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) integer;
    }
}
