package expression.with_generics;

import expression.type.AbstractType;
import expression.type.IntegerType;

public class MinWithGenerics<T> extends OperatorWithGenerics<T>{
    public MinWithGenerics(MultyExpressionWithGenerics<T> first, MultyExpressionWithGenerics<T> second, AbstractType<T> type) {
        super(first, second, type);
        operation = " min ";
    }

    @Override
    public int makeOperation(int first, int second) {
        return new IntegerType(true).min(first, second);
    }

    @Override
    public T makeOperation(T first, T second) {
        return type.min(first, second);
    }
}