package expression.generic;

import expression.with_generics.ExpressionParserWithGenerics;
import expression.type.*;
import expression.with_generics.MultyExpressionWithGenerics;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        AbstractType<?> type = switch (mode) {
            case "d" -> new DoubleType();
            case "bi" -> new BigIntegerType();
            case "u" -> new IntegerType(false);
            case "s" -> new ShortType();
            case "l" -> new LongType();
            default -> new IntegerType(true);
        };
        return getTable(type, expression, x1, x2, y1, y2, z1, z2);
    }

    public <T> Object[][][] getTable(AbstractType<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        ExpressionParserWithGenerics<T> parser = new ExpressionParserWithGenerics<>(type);
        MultyExpressionWithGenerics<T> exp = parser.parse(expression);
        Object[][][] R = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        R[i - x1][j - y1][k - z1] = exp.evaluate(type.fromInt(i), type.fromInt(j), type.fromInt(k));
                    } catch (Throwable e){
                        R[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return R;
    }
}
