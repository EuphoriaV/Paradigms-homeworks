package expression.with_generics;

import expression.type.AbstractType;
import expression.type.IntegerType;

public class MaxWithGenerics<T> extends OperatorWithGenerics<T>{
    public MaxWithGenerics(MultyExpressionWithGenerics<T> first, MultyExpressionWithGenerics<T> second, AbstractType<T> type) {
        super(first, second, type);
        operation = " max ";
    }

    @Override
    public int makeOperation(int first, int second) {
        return new IntegerType(true).max(first, second);
    }

    @Override
    public T makeOperation(T first, T second) {
        return type.max(first, second);
    }
}
