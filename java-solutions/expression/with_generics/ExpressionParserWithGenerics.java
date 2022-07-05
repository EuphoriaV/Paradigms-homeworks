package expression.with_generics;

import expression.type.AbstractType;
import expression.exceptions.*;

public class ExpressionParserWithGenerics<T> extends BaseParser implements TripleParser {
    private Exp curExp;
    private MultyExpressionWithGenerics<T> curExpression;
    private final AbstractType<T> type;

    public ExpressionParserWithGenerics(AbstractType<T> type) {
        this.type = type;
    }

    @Override
    public MultyExpressionWithGenerics<T> parse(String string) {
        checkValid(string);
        makeParser(new StringSource(string));
        try {
            return parseExpression(3);
        } catch (StackOverflowError e) {
            throw new InvalidExpressionException("invalid expression");
        }
    }

    private void parseConst(boolean negative) {
        StringBuilder sb = new StringBuilder();
        if (negative) {
            sb.append('-');
        }
        while (between('0', '9')) {
            sb.append(take1());
        }
        if (Character.isWhitespace(ch)) {
            take();
        }
        if (between('0', '9')) {
            throw new InvalidExpressionException("2 consts in a row");
        }
        try {
            curExpression = new ConstWithGenerics<>(type.parse(sb.toString()));
        } catch (NumberFormatException e) {
            throw new InvalidConstException("invalid const");
        }
        curExp = Exp.CONST;
    }

    private void parseExp() {
        if (between('0', '9')) {
            parseConst(false);
        } else if (between('x', 'z')) {
            curExpression = new VariableWithGenerics<>(String.valueOf(take()));
            curExp = Exp.VARIABLE;
        } else {
            switch (take()) {
                case '\0':
                    return;
                case '+':
                    curExp = Exp.ADD;
                    return;
                case '-':
                    curExp = Exp.SUB;
                    return;
                case '*':
                    curExp = Exp.MUL;
                    return;
                case '/':
                    curExp = Exp.DIV;
                    return;
                case 'm':
                    if (take('i')) {
                        if (take('n')) {
                            curExp = Exp.MIN;
                            return;
                        }
                    } else if (take('a')) {
                        if (take('x')) {
                            curExp = Exp.MAX;
                            return;
                        }
                    }
                case 'c':
                    if (take('o')) {
                        if (take('u')) {
                            if (take('n')) {
                                if (take('t')) {
                                    curExp = Exp.COUNT;
                                    return;
                                }
                            }
                        }
                    }
                case '(':
                    curExp = Exp.LEFT;
                    return;
                case ')':
                    curExp = Exp.RIGHT;
                    return;
            }
            throw new InvalidExpressionException("invalid expression");
        }
    }

    private MultyExpressionWithGenerics<T> parseExpression(int priority) {
        if (priority == 0) {
            return parseLowPriority();
        } else {
            MultyExpressionWithGenerics<T> res = parseExpression(priority - 1);
            while (inPriority(priority)) {
                Exp cur = curExp;
                MultyExpressionWithGenerics<T> second = parseExpression(priority - 1);
                res = switch (cur) {
                    case ADD -> new CheckedAddWithGenerics<>(res, second, type);
                    case SUB -> new CheckedSubtractWithGenerics<>(res, second, type);
                    case MUL -> new CheckedMultiplyWithGenerics<>(res, second, type);
                    case DIV -> new CheckedDivideWithGenerics<>(res, second, type);
                    case MAX -> new MaxWithGenerics<>(res, second, type);
                    case MIN -> new MinWithGenerics<>(res, second, type);
                    default -> throw new InvalidOperatorException("invalid operator");
                };
            }
            return res;
        }
    }

    private MultyExpressionWithGenerics<T> parseLowPriority() {
        parseExp();
        switch (curExp) {
            case CONST:
                MultyExpressionWithGenerics<T> cur = curExpression;
                parseExp();
                return cur;
            case VARIABLE:
                MultyExpressionWithGenerics<T> curr = curExpression;
                parseExp();
                return curr;
            case SUB:
                if (between('0', '9')) {
                    parseConst(true);
                    cur = curExpression;
                    parseExp();
                    return cur;
                }
                return new CheckedNegateWithGenerics<>(parseLowPriority(),type);
            case COUNT:
                return new CountWithGenerics<>(parseLowPriority(),type);
            case LEFT:
                cur = parseExpression(3);
                parseExp();
                return cur;
        }
        throw new InvalidExpressionException("invalid expression");
    }


    private boolean inPriority(int priority) {
        return switch (priority) {
            case 1 -> curExp == Exp.MUL || curExp == Exp.DIV;
            case 2 -> curExp == Exp.ADD || curExp == Exp.SUB;
            case 3 -> curExp == Exp.MAX || curExp == Exp.MIN;
            default -> false;
        };
    }

    private void checkValid(String string) {
        int left = 0, right = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '(') {
                left++;
            }
            if (string.charAt(i) == ')') {
                right++;
            }
            if (i > 0 && string.charAt(i) == 'm' && string.charAt(i - 1) >= '0' && string.charAt(i - 1) <= '9') {
                throw new InvalidExpressionException("invalid expression");
            }
        }
        if (left != right) {
            throw new BracketsException("amount of left brackets doesn't equal to amount of right brackets");
        }
    }
}
