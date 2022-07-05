"use strict"
const variables = {"x": 0, "y": 1, "z": 2};
let cnst = x => (...args) => x;
let variable = x => (...args) => args[variables[x]];
let binaryOperation = (f, x, y) => (...args) => f(x(...args), y(...args));
let unaryOperation = (f, x) => (...args) => (f(x(...args)));
let add = (x, y) => binaryOperation((a, b) => (a + b), x, y);
let subtract = (x, y) => binaryOperation((a, b) => (a - b), x, y);
let multiply = (x, y) => binaryOperation((a, b) => (a * b), x, y);
let divide = (x, y) => binaryOperation((a, b) => (a / b), x, y);
let negate = x => unaryOperation(a => -a, x);
let sinh = x => unaryOperation(a => Math.sinh(a), x);
let cosh = x => unaryOperation(a => Math.cosh(a), x);
let pi = (...args) => Math.PI;
let e = (...args) => Math.E;
//test
let test = add(multiply(variable("x"), variable("x")), add(multiply(cnst(-2), variable("x")), cnst(1)));
for (let i = 0; i <= 10; i++) {
    println(test(i));
}