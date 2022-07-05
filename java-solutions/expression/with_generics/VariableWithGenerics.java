package expression.with_generics;

import java.util.Objects;

public class VariableWithGenerics<T> implements MultyExpressionWithGenerics<T> {
    private final String str;

    public VariableWithGenerics(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z){
        return switch (str) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }
    @Override
    public T evaluate(T x) {
        return x;
    }

    @Override
    public T evaluate(T x, T y, T z){
        return switch (str) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof VariableWithGenerics<?>){
            return Objects.equals(str, ((VariableWithGenerics<?>) o).str);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }
}
