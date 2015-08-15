(ns modest-let-flow.core
  (:require [manifold.deferred]))


(defn- expand-let-flow [chain-fn zip-fn bindings body]
  (let [vars (->> bindings (partition 2) (map first))]
    (if (empty? bindings)
      `(manifold.deferred/future ~@body)
      `(let ~bindings
         (~chain-fn (~zip-fn ~@vars)
           (fn [[~@vars]]
             ~@body))))))


(defmacro let-flow [bindings & body]
  (expand-let-flow 'manifold.deferred/chain
                   'manifold.deferred/zip
                   bindings
                   body))


(defmacro let-flow' [bindings & body]
  (expand-let-flow 'manifold.deferred/chain'
                   'manifold.deferred/zip'
                   bindings
                   body))
