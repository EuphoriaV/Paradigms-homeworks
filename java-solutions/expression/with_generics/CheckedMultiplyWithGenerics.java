package expression.with_generics;

import expression.type.AbstractType;
import expression.type.IntegerType;

public class CheckedMultiplyWithGenerics<T> extends OperatorWithGenerics<T> {
    public CheckedMultiplyWithGenerics(MultyExpressionWithGenerics<T> first, MultyExpressionWithGenerics<T> second, AbstractType<T> type) {
        super(first, second, type);
        operation = " * ";
    }

    @Override
    public int makeOperation(int first, int second) { return new IntegerType(true).multiply(first, second); }

    @Override
    public T makeOperation(T first, T second) {
        return type.multiply(first, second);
    }
}
