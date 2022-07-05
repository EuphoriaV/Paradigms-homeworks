package expression.with_generics;

import expression.type.AbstractType;
import expression.type.IntegerType;

public class CheckedSubtractWithGenerics<T> extends OperatorWithGenerics<T> {
    public CheckedSubtractWithGenerics(MultyExpressionWithGenerics<T> first, MultyExpressionWithGenerics<T> second, AbstractType<T> type) {
        super(first, second, type);
        operation = " - ";
    }

    @Override
    public int makeOperation(int first, int second) {
        return new IntegerType(true).subtract(first,second);
    }

    @Override
    public T makeOperation(T first, T second) { return type.subtract(first,second); }
}
