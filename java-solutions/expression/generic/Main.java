package expression.generic;

import expression.exceptions.ExpressionParser;
import expression.type.DoubleType;
import expression.with_generics.ExpressionParserWithGenerics;

public class Main {
    public static void main(String[] args) {
        GenericTabulator tabulator = new GenericTabulator();
        Object[][][] r = tabulator.tabulate(args[0].substring(1),args[1],-2,2,-2,2,-2,2);
        for (Object[][] objects : r) {
            for (Object[] object : objects) {
                for (Object o : object) {
                    System.out.print(o + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}