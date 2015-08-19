(ns modest-let-flow.core
  (:require [manifold.deferred]))


(defn- expand-let-flow-1 [chain-fn bindings body]
  (let [[[var val] & rest-of-bindings] bindings]
      `(~chain-fn ~val
         (fn [~var]
           ~@(if (nil? rest-of-bindings)
               body
               (list (expand-let-flow-1 chain-fn
                                        rest-of-bindings
                                        body)))))))


(defn- expand-let-flow [chain-fn bindings body]
  (if (empty? bindings)
    `(manifold.deferred/future ~@body)
    (expand-let-flow-1 chain-fn
                       (partition 2 bindings)
                       body)))


(defmacro let-flow [bindings & body]
  (expand-let-flow 'manifold.deferred/chain
                   bindings
                   body))


(defmacro let-flow' [bindings & body]
  (expand-let-flow 'manifold.deferred/chain'
                   bindings
                   body))
