"use strict"
const variables = {
    "x": 0,
    "y": 1,
    "z": 2
};

function Const(x) {
    this.x = x;
    this.evaluate = function() {
        return this.x;
    }
    this.toString = this.prefix = function() {
        return this.x + "";
    }
}

function Variable(x) {
    this.x = x;
    this.evaluate = function(...args) {
        return args[variables[this.x]];
    }
    this.toString = this.prefix = function() {
        return this.x;
    }
}

function Operation(f, operation, ...args) {
    this.mas = args;
    this.f = f;
    this.operation = operation;
    this.evaluate = function(...args1) {
        return this.f(...this.mas.map(a => a.evaluate(...args1)));
    }
    this.toString = function() {
        let s = "";
        for (let i = 0; i < this.mas.length; i++) {
            s += this.mas[i].toString() + " ";
        }
        s += this.operation;
        return s;
    }
    this.prefix = function() {
        let s = "(" + operation;
        for (let i = 0; i < this.mas.length; i++) {
            s += " " + this.mas[i].prefix();
        }
        s += ")";
        return s;
    }
}

const mean = function(...args) {
    let sum = 0;
    for (let i = 0; i < args.length; i++) {
        sum += args[i];
    }
    return sum / args.length;
}

const disp = function(...args) {
    let sum1 = 0;
    let sum2 = 0;
    for (let i = 0; i < args.length; i++) {
        sum1 += args[i] * args[i];
        sum2 += args[i];
    }
    return (sum1 / args.length) - (sum2 / args.length) * (sum2 / args.length);
}

function Mean(...args) {
    return new Operation(mean, "mean", ...args);
}

function Var(...args) {
    return new Operation(disp, "var", ...args);
}

function Add(x, y) {
    return new Operation((a, b) => a + b, "+", x, y);
}

function Subtract(x, y) {
    return new Operation((a, b) => a - b, "-", x, y);
}

function Multiply(x, y) {
    return new Operation((a, b) => a * b, "*", x, y);
}

function Divide(x, y) {
    return new Operation((a, b) => a / b, "/", x, y);
}

function Negate(x) {
    return new Operation((a) => -a, "negate", x);
}

function Sinh(x) {
    return new Operation((a) => Math.sinh(a), "sinh", x);
}

function Cosh(x) {
    return new Operation((a) => Math.cosh(a), "cosh", x);
}

const operations = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "sinh": Sinh,
    "cosh": Cosh,
    "mean": Mean,
    "var": Var
};

const numOfArguments = {
    "+": 2,
    "-": 2,
    "*": 2,
    "/": 2,
    "negate": 1,
    "sinh": 1,
    "cosh": 1,
}

const parse = function(s) {
    const mas = s.trim().split(/\s+/g);
    const stack = [];
    for (let i = 0; i < mas.length; i++) {
        if (!isNaN(parseInt(mas[i]))) {
            stack.push(new Const(parseInt(mas[i])));
        } else if (mas[i] in variables) {
            stack.push(new Variable(mas[i]));
        } else if (mas[i] in operations) {
            let arr = [];
            arr.push(stack.pop());
            if (mas[i].length === 1) {
                arr.unshift(stack.pop());
            }
            stack.push(new operations[mas[i]](...arr));
        }
    }
    return stack[0];
}

const InvalidException = function(message) {
    Error.call(this, message);
}

let pos;

const parseSingle = function(mas) {
    if (mas[pos] === "(") {
        if (mas[++pos] in operations) {
            const operation = mas[pos++];
            let arr = [];
            while (mas[pos] !== ")") {
                arr.push(parseSingle(mas));
            }
            if (operation in numOfArguments && arr.length !== numOfArguments[operation]) {
                throw new InvalidExpressionException("Operation has wrong amount of arguments");
            }
            pos++;
            return new operations[operation](...arr);
        } else {
            throw new InvalidExpressionException("Invalid operation");
        }
    } else if (!isNaN(parseInt(mas[pos]))) {
        let ans = new Const(parseInt(mas[pos]));
        if (ans.toString() === mas[pos]) {
            pos++;
            return ans;
        }
        throw new InvalidExpressionException("Invalid const");
    } else if (mas[pos] in variables) {
        return new Variable(mas[pos++]);
    } else {
        throw new InvalidExpressionException("Invalid expression");
    }
}

const parsePrefix = function(s) {
    pos = 0;
    s = s.replaceAll("(", " ( ").replaceAll(")", " ) ").trim();
    let mas = s.split(/\s+/g);
    let balance = 0;
    for (let i = 0; i < mas.length; i++) {
        if (mas[i] === "(") {
            balance++;
        } else if (mas[i] === ")") {
            balance--;
        }
        if (balance < 0) {
            throw new InvalidExpressionException("Invalid bracket sequence");
        }
    }
    if (balance === 0) {
        const ans = parseSingle(mas);
        if (pos === mas.length) {
            return ans;
        }
    }
    throw new InvalidExpressionException("Amounts of left and right brackets must be equal");
}