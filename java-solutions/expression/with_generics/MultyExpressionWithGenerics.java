package expression.with_generics;

import expression.MultyExpression;

public interface MultyExpressionWithGenerics<T> extends MultyExpression {
    T evaluate(T x);
    T evaluate(T x, T y, T z);
}
