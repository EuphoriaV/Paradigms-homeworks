(defn operation [f]
    (fn [& args]
        (if (every? number? args)
            (apply f args)
            (apply mapv (operation f) args)
        )
    )
)
(def v+ (operation +))
(def v- (operation -))
(def v* (operation *))
(def vd (operation /))
(defn scalar [a b] (reduce + (v* a b)))
(defn vect [a b]
    (vector
        (- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
        (- (- (* (nth a 0) (nth b 2)) (* (nth a 2) (nth b 0))))
        (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))
    )
)
(defn v*s [a b] (mapv (partial * b) a))
(def m+ (operation +))
(def m- (operation -))
(def m* (operation *))
(def md (operation /))
(defn m*s [a b] (mapv (fn a [vector](v*s vector b)) a))
(defn m*v [a b] (mapv (partial scalar b) a))
(defn transpose [a] (apply mapv vector a))
(defn m*m [a b] (transpose (mapv (partial m*v a) (transpose b))))
(def s+ (operation +))
(def s- (operation -))
(def s* (operation *))
(def sd (operation /))
;; [[1 2 3]         [[1 4 7]
;;  [4 5 6]   ->     [2 5 8]
;;  [7 8 9]]         [3 6 9]]