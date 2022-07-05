;;HW 10-----------------------------------------------------------
(defn constant [a] (fn [xyz] a))
(defn variable [a] (fn [xyz] (xyz a)))
(defn binOperation [f]
    (fn [a b]
        (fn [xyz]
            (f (a xyz) (b xyz))
        )
    )
)
(defn unOperation [f]
    (fn [a]
        (fn [xyz]
            (f (a xyz))
        )
    )
)
(defn powFunction[a b] (Math/pow a b))
(defn logFunction[a b] (/ (Math/log (Math/abs b)) (Math/log (Math/abs a))))
(def add (binOperation +))
(def pow (binOperation powFunction))
(def log (binOperation logFunction))
(def subtract (binOperation -))
(def multiply (binOperation *))
(def divide (binOperation (fn [a b] (/ (double a) (double b)))))
(def negate (unOperation -))
(def operations
    {"+" add
     "-" subtract
     "*" multiply
     "/" divide
     "pow" pow
     "log" log
     "negate" negate}
)
(defn parseFunction [expression]
    (letfn [(parse [s]
            (cond
                (number? s) (constant s)
                (or (= (str s) "x" ) (= (str s) "y" ) (= (str s) "z" )) (variable (str s))
                :else (apply (get operations (str (nth s 0))) (mapv parse (pop s)))
            )
        )]
        (parse (read-string expression))
    )
)
;;HW11---------------------------------------------------------
(defn proto-get
    ([obj key] (proto-get obj key nil))
    ([obj key default]
    (cond
        (contains? obj key) (obj key)
        (contains? obj :prototype) (proto-get (obj :prototype) key default)
        :else default)))
(defn proto-call
    [this key & args]
    (apply (proto-get this key) this args))
(defn field [key]
    (fn
        ([this] (proto-get this key))
        ([this def] (proto-get this key def))))
(defn method
    [key] (fn [this & args] (apply proto-call this key args)))
(defn constructor
    [ctor prototype]
    (fn [& args] (apply ctor {:prototype prototype} args)))
(def evaluate(method :evaluate))
(def toString(method :toString))
(def diff(method :diff))
(def toStringSuffix(method :toStringSuffix))

(def Constant nil)
(def ConstantPrototype {
    :evaluate (fn [num xyz] ((field :num) num))
    :toString (fn [num] (str ((field :num) num)))
    :toStringSuffix (fn [num] (str ((field :num) num)))
    :diff (fn [num var] (Constant 0))
    })
(defn ConstantConstructor [this num] (assoc this :num num))
(def Constant (constructor ConstantConstructor ConstantPrototype))

(defn getFirst [var] (clojure.string/lower-case (first var)))
(def VariablePrototype {
    :evaluate (fn [vars xyz] (xyz (getFirst ((field :vars) vars))))
    :toString (fn [vars] ((field :vars) vars))
    :toStringSuffix (fn [vars] ((field :vars) vars))
    :diff (fn [vars var] (cond
                (= (getFirst ((field :vars) vars)) var) (Constant 1)
                :else (Constant 0)
          ))
    })
(defn VariableConstructor [this vars] (assoc this :vars vars))
(def Variable (constructor VariableConstructor VariablePrototype))

(defn evaluateOperation [f] (fn [expression xyz] (f (evaluate ((field :first) expression) xyz) (evaluate ((field :second) expression) xyz))))
(defn toStringOperation [f] (fn [expression] (str "(" f " " (toString ((field :first) expression)) " " (toString ((field :second) expression)) ")") ))
(defn toStringSuffixOperation [f] (fn [expression] (str "(" (toStringSuffix ((field :first) expression)) " " (toStringSuffix ((field :second) expression)) " " f ")") ))
(defn OperationConstructor [this first second] (assoc this :first first :second second))

(def Add nil)
(def AddPrototype {
    :evaluate (evaluateOperation +)
    :toString (toStringOperation "+")
    :toStringSuffix (toStringSuffixOperation "+")
    :diff (fn [expression var] (Add (diff ((field :first) expression) var) (diff ((field :second) expression) var)))
    })
(def Add (constructor OperationConstructor AddPrototype))

(def Subtract nil)
(def SubtractPrototype {
    :evaluate (evaluateOperation -)
    :toString (toStringOperation "-")
    :toStringSuffix (toStringSuffixOperation "-")
    :diff (fn [expression var] (Subtract (diff ((field :first) expression) var) (diff ((field :second) expression) var)))
    })
(def Subtract (constructor OperationConstructor SubtractPrototype))

(def Multiply nil)
(def MultiplyPrototype {
    :evaluate (evaluateOperation *)
    :toString (toStringOperation "*")
    :toStringSuffix (toStringSuffixOperation "*")
    :diff (fn [expression var] (Add
        (Multiply (diff ((field :first) expression) var) ((field :second) expression))
        (Multiply (diff ((field :second) expression) var) ((field :first) expression))))
    })
(def Multiply (constructor OperationConstructor MultiplyPrototype))

(def Divide nil)
(def DividePrototype {
    :evaluate (fn [expression xyz] (/ (double (evaluate ((field :first) expression) xyz)) (double (evaluate ((field :second) expression) xyz))))
    :toString (toStringOperation "/")
    :toStringSuffix (toStringSuffixOperation "/")
    :diff (fn [expression var] (Divide
        (Subtract
                (Multiply (diff ((field :first) expression) var) ((field :second) expression))
                (Multiply (diff ((field :second) expression) var) ((field :first) expression)))
        (Multiply ((field :second) expression) ((field :second) expression))))
    })
(def Divide (constructor OperationConstructor DividePrototype))

(def Negate nil)
(def NegatePrototype {
    :evaluate (fn [expression xyz] (- (evaluate ((field :first) expression) xyz)))
    :toString (fn [expression] (str "(negate " (toString ((field :first) expression)) ")"))
    :toStringSuffix (fn [expression] (str "(" (toStringSuffix ((field :first) expression)) " negate)"))
    :diff (fn [expression var] (Negate (diff ((field :first) expression) var)))
    })
(defn NegateConstructor [this first] (assoc this :first first))
(def Negate (constructor NegateConstructor NegatePrototype))

(def E (Constant (Math/E)))
(def Log nil)
(def Pow nil)
(def PowPrototype {
    :evaluate (evaluateOperation powFunction)
    :toString (toStringOperation "pow")
    :diff (fn [expression var] (Multiply
                                           (Pow  ((field :first) expression) ((field :second) expression))
                                           (Add
                                                (Multiply (diff ((field :second) expression) var) (Log E ((field :first) expression)))
                                                (Divide
                                                      (Multiply ((field :second) expression) (diff ((field :first) expression) var))
                                                      ((field :first) expression))
                                               )))
        })
(def Pow (constructor OperationConstructor PowPrototype))

(def LogPrototype {
    :evaluate (evaluateOperation logFunction)
    :toString (toStringOperation "log")
    :diff (fn [expression var]
        (Divide
            (Subtract
                (Divide
                    (Multiply (Log E ((field :first) expression)) (diff ((field :second) expression) var))
                    ((field :second) expression))
                (Divide
                    (Multiply (Log E ((field :second) expression)) (diff ((field :first) expression) var))
                    ((field :first) expression))
            )
            (Multiply (Log E ((field :first) expression)) (Log E ((field :first) expression)))
            ))
    })
(def Log (constructor OperationConstructor LogPrototype))

(defn bitAndFunction [a b] (Double/longBitsToDouble (clojure.core/bit-and (Double/doubleToLongBits a) (Double/doubleToLongBits b))))
(defn bitOrFunction [a b] (Double/longBitsToDouble (clojure.core/bit-or (Double/doubleToLongBits a) (Double/doubleToLongBits b))))
(defn bitXorFunction [a b] (Double/longBitsToDouble (clojure.core/bit-xor (Double/doubleToLongBits a) (Double/doubleToLongBits b))))

(def BitAndPrototype {
    :evaluate (evaluateOperation bitAndFunction)
    :toString (toStringOperation "&")
    :toStringSuffix (toStringSuffixOperation "&")
    })
(def BitAnd (constructor OperationConstructor BitAndPrototype))

(def BitOrPrototype {
    :evaluate (evaluateOperation bitOrFunction)
    :toString (toStringOperation "|")
    :toStringSuffix (toStringSuffixOperation "|")
    })
(def BitOr (constructor OperationConstructor BitOrPrototype))

(def BitXorPrototype {
    :evaluate (evaluateOperation bitXorFunction)
    :toString (toStringOperation "^")
    :toStringSuffix (toStringSuffixOperation "^")
    })
(def BitXor (constructor OperationConstructor BitXorPrototype))

(def ops
    {"+" Add
    "-" Subtract
    "*" Multiply
    "/" Divide
    "&" BitAnd
    "|" BitOr
    "^" BitXor
    "negate" Negate
    "pow" Pow
    "log" Log}
)
(defn parseObject [expression]
    (letfn [(parse [s]
            (cond
                (number? s) (Constant s)
                (or (= (str s) "x" ) (= (str s) "y" ) (= (str s) "z" )) (Variable (str s))
                :else (apply (get ops (str (nth s 0))) (mapv parse (pop s)))
            )
        )]
        (parse (read-string expression))
    )
)
;;HW12-------------------------------------------------------------
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)
(defn _empty [value] (partial -return value))
(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))
(defn _map [f]
  (fn [result]
    (if (-valid? result)
      (-return (f (-value result)) (-tail result)))))
(defn _combine [f a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar)
        ((_map (partial f (-value ar)))
          ((force b) (-tail ar)))))))
(defn _either [a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p]
  (fn [input]
    (-value ((_combine (fn [v _] v) p (_char #{\u0001})) (str input \u0001)))))
(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (_map f) parser))
(def +ignore (partial +map (constantly 'ignore)))
(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(def +not (fn [p] (comp not p)))
(def +if-not (fn [p1 p2] (fn [input] (if (-valid? ((+not p1) input)) (p2 input)))))
(defn +or [p & ps]
  (reduce (partial _either) p ps))
(defn +opt [p]
  (+or p (_empty nil)))
(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))

(def *digit (+char "0123456789.-"))
(def *number (+map read-string (+str (+plus *digit))))
(def *string
    (+seqn 1
        (+char "\"")
        (+str (+star (+char-not "\"")))
        (+char "\"")))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
(def *null (+seqf (constantly 'null) (+char "n") (+char "u") (+char "l") (+char "l")))
(def *variable (+str (+plus (+char "xyzXYZ"))))
(def *leftBracket (+ignore (+char "(")))
(def *rightBracket (+ignore (+char ")")))
(def *parseExpression nil)
(def *parseSingle (+or *number *variable (delay *parseExpression)))
(defn *parseWord [s] (apply +seqf str (mapv +char (mapv str (seq s)))))
(def *parseOperation (+or (+char "+-*/&|^") (*parseWord "negate")))
(def *parseUnary (+seq *ws *leftBracket *ws *parseSingle *ws *parseOperation *ws *rightBracket *ws))
(def *parseBinary (+seq *ws *leftBracket *ws *parseSingle *ws *parseSingle *ws *parseOperation *ws *rightBracket *ws))
(def *parseExpression (+or *parseUnary *parseBinary (+seqn 0 *ws *parseSingle *ws)))
(defn parseObjectSuffix [expression]
    (letfn [(parse [s]
            (cond
                (number? s) (Constant s)
                (string? s) (Variable (str s))
                :else (apply (get ops (str (nth s (- (count s) 1)))) (mapv parse (drop-last s)))
            )
        )]
        (parse (-value (*parseExpression expression)))
    )
)