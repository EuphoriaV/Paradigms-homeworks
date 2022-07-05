package expression.type;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class IntegerType extends AbstractType<Integer> {
    private final boolean check;

    public IntegerType(boolean check) {
        this.check = check;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        if (check) {
            if ((x > 0 && y > 0 && x + y <= 0) || (x < 0 && y < 0 && x + y >= 0)) {
                throw new OverflowException("overflow");
            }
        }
        return x + y;
    }

    @Override
    public Integer fromInt(int a) {
        return Integer.valueOf(a);
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (check) {
            if ((x < 0 && y > 0 && (x - y >= 0)) || (x > 0 && y < 0 && (x - y <= 0)) || (x == 0 && y == Integer.MIN_VALUE)) {
                throw new OverflowException("overflow");
            }
        }
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (check) {
            if (x != 0 && y != 0 && ((x * y) / x != y || (x * y) / y != x)) {
                throw new OverflowException("overflow");
            }
        }
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (check) {
            if (x == Integer.MIN_VALUE && y == -1) {
                throw new OverflowException("overflow");
            }
            if (y == 0) {
                throw new DivisionByZeroException("division by zero");
            }
        }
        return x / y;
    }

    @Override
    public Integer count(Integer left) {
        return Integer.bitCount(left);
    }

    @Override
    public Integer negate(Integer x) {
        if (check) {
            if (x == Integer.MIN_VALUE) {
                throw new OverflowException("overflow");
            }
        }
        return -x;
    }

    @Override
    public Integer min(Integer left, Integer right) {
        return Integer.min(left, right);
    }

    @Override
    public Integer max(Integer left, Integer right) {
        return Integer.max(left, right);
    }

    @Override
    public Integer parse(String s) {
        return Integer.parseInt(s);
    }

}
