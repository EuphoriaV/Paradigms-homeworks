package expression.type;

public class ShortType extends AbstractType<Short> {
    @Override
    public Short fromInt(int a) {
        return Short.valueOf((short) a);
    }

    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) {
        return (short) (x / y);
    }

    @Override
    public Short max(Short x, Short y) {
        return (short) Integer.max(x, y);
    }

    @Override
    public Short min(Short x, Short y) {
        return (short) Integer.min(x, y);
    }

    @Override
    public Short negate(Short x) {
        return (short) -x;
    }

    @Override
    public Short count(Short x) {
        return (short) Integer.bitCount(x & 0xffff);
    }

    @Override
    public Short parse(String s) {
        return (short) Integer.parseInt(s);
    }

}
