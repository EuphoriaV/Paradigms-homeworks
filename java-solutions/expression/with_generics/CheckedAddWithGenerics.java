package expression.with_generics;

import expression.type.AbstractType;
import expression.type.IntegerType;

public class CheckedAddWithGenerics<T> extends OperatorWithGenerics<T> {
    public CheckedAddWithGenerics(MultyExpressionWithGenerics<T> first, MultyExpressionWithGenerics<T> second, AbstractType<T> type) {
        super(first, second, type);
        operation = " + ";
    }

    @Override
    public int makeOperation(int first, int second) {
        return new IntegerType(true).add(first, second);
    }

    @Override
    public T makeOperation(T first, T second) {
        return type.add(first, second);
    }
}
