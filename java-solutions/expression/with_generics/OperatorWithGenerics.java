package expression.with_generics;

import expression.type.AbstractType;

import java.util.Objects;

public abstract class OperatorWithGenerics<T> implements MultyExpressionWithGenerics<T> {
    protected MultyExpressionWithGenerics<T> first;
    protected MultyExpressionWithGenerics<T> second;
    protected AbstractType<T> type;
    protected String operation;

    abstract public int makeOperation(int first, int second);

    abstract public T makeOperation(T first, T second);

    public OperatorWithGenerics(MultyExpressionWithGenerics<T> first, MultyExpressionWithGenerics<T> second, AbstractType<T> type) {
        this.first = first;
        this.second = second;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + first + operation + second + ")";
    }

    @Override
    public int evaluate(int x) {
        return makeOperation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return makeOperation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public T evaluate(T x) {
        return makeOperation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return makeOperation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof OperatorWithGenerics) {
            return first.equals(((OperatorWithGenerics<?>) o).first) && second.equals(((OperatorWithGenerics<?>) o).second) && operation.equals(((OperatorWithGenerics<?>) o).operation);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, second, first);
    }
}
