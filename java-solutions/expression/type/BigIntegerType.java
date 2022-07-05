package expression.type;

import java.math.BigInteger;

public class BigIntegerType extends AbstractType<BigInteger> {
    @Override
    public BigInteger fromInt(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger min(BigInteger x, BigInteger y) {
        return x.min(y);
    }

    @Override
    public BigInteger max(BigInteger x, BigInteger y) {
        return x.max(y);
    }

    @Override
    public BigInteger count(BigInteger x) {
        return BigInteger.valueOf(x.bitCount());
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger parse(String s) {
        return new BigInteger(s);
    }
}
