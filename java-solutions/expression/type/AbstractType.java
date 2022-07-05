package expression.type;

public abstract class AbstractType<T> {
    public abstract T fromInt(int a);

    public abstract T add(T x, T y);

    public abstract T subtract(T x, T y);

    public abstract T multiply(T x, T y);

    public abstract T divide(T x, T y);

    public abstract T max(T x, T y);

    public abstract T min(T x, T y);

    public abstract T count(T x);

    public abstract T negate(T x);

    public abstract T parse(String s);
}
