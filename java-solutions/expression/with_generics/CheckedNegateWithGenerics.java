package expression.with_generics;

import expression.Negate;
import expression.type.AbstractType;
import expression.type.IntegerType;

import java.util.Objects;

public class CheckedNegateWithGenerics<T>implements MultyExpressionWithGenerics<T> {
    private final AbstractType<T> type;
    private final MultyExpressionWithGenerics<T> first;

    public CheckedNegateWithGenerics(MultyExpressionWithGenerics<T> first, AbstractType<T> type) {
        this.first = first;
        this.type = type;
    }

    @Override
    public String toString() {
        return "-(" + first + ")";
    }

    public int makeOperation(int x) {
        return new IntegerType(true).negate(x);
    }

    public T makeOperation(T x) {
        return type.negate(x);
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
        if (o instanceof CheckedNegateWithGenerics<?>) {
            return first.equals(((CheckedNegateWithGenerics<?>) o).first);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first);
    }
}
