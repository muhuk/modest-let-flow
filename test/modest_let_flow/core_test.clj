(ns modest-let-flow.core-test
  (:require [clojure.test :refer :all]
            [manifold.deferred :as d]
            [modest-let-flow.core :refer :all]))


(deftest test-let-flow
  (is (= 2
         @(let-flow []
            (inc 1))))
  (is (= 2
         @(let-flow [x 1]
            (inc x))))
  (is (= 2
         @(let-flow [x (d/future 1)]
            (inc x))))
  (is (= 2
         @(let [x (d/future 1)]
            (let-flow []
              (inc @x)))))
  (is (= 5
         @(let [x (d/future 2)]
            (let-flow [y (d/future 3)]
              (+ @x y)))))
  (is (= 9
         @(let [x (d/future 2)]
            (let-flow [y (d/future 3)]
              (let-flow [z (d/future 4)]
                (+ @x y z))))))
  (is (= 9
         @(let-flow [x (d/future 2)
                     [y z] (d/future (vector (inc x) 4))]
            (+ x y z)))))


(deftest test-let-flow'
  (is (= 2
         @(let-flow' []
            (inc 1))))
  (is (= 2
         @(let-flow' [x 1]
            (inc x))))
  (is (= 2
         @(let-flow' [x (d/future 1)]
            (inc x))))
  (is (= 2
         @(let [x (d/future 1)]
            (let-flow' []
              (inc @x)))))
  (is (= 5
         @(let [x (d/future 2)]
            (let-flow' [y (d/future 3)]
              (+ @x y)))))
  (is (= 9
         @(let-flow' [x (d/future 2)
                      [y z] (d/future (vector (inc x) 4))]
            (+ x y z)))))
