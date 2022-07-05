package expression.type;

public class LongType extends AbstractType<Long> {
    @Override
    public Long fromInt(int a) {
        return Long.valueOf(a);
    }
    
    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long divide(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long max(Long x, Long y) {
        return Long.max(x, y);
    }

    @Override
    public Long negate(Long x) {
        return -x;
    }
    
    @Override
    public Long min(Long x, Long y) {
        return Long.min(x, y);
    }

    @Override
    public Long count(Long x) {
        return (long) Long.bitCount(x);
    }

    @Override
    public Long parse(String s) {
        return Long.parseLong(s);
    }

}
