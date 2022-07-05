package expression.with_generics;

import expression.type.AbstractType;
import expression.type.IntegerType;

public class CheckedDivideWithGenerics<T> extends OperatorWithGenerics<T> {
    public CheckedDivideWithGenerics(MultyExpressionWithGenerics<T> first, MultyExpressionWithGenerics<T> second, AbstractType<T> type) {
        super(first, second, type);
        operation = " / ";
    }

    @Override
    public int makeOperation(int first, int second) {
        return new IntegerType(true).divide(first, second);
    }

    @Override
    public T makeOperation(T first, T second) {
        return type.divide(first, second);
    }
}
