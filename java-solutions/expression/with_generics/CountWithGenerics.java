package expression.with_generics;

import expression.type.AbstractType;
import expression.type.IntegerType;

import java.util.Objects;

public class CountWithGenerics<T> implements MultyExpressionWithGenerics<T> {
    private final AbstractType<T> type;
    private final MultyExpressionWithGenerics<T> first;

    public CountWithGenerics(MultyExpressionWithGenerics<T> first, AbstractType<T> type) {
        this.first = first;
        this.type = type;
    }

    @Override
    public String toString() {
        return "count(" + first + ")";
    }

    public int makeOperation(int x) {
        return new IntegerType(true).count(x);
    }

    public T makeOperation(T x) {
        return type.count(x);
    }

    @Override
    public int evaluate(int x) {
        return makeOperation(first.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return makeOperation(first.evaluate(x, y, z));
    }

    @Override
    public T evaluate(T x) {
        return makeOperation(first.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return makeOperation(first.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CountWithGenerics<?>) {
            return first.equals(((CountWithGenerics<?>) o).first);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first);
    }
}
