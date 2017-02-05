(ns cljsed.remotes
  (:require [cljsed.login.java.validators :as v]
            [cljsed.utils :refer [parse-integer parse-double]]
            [compojure.handler :refer [site]]
            [shoreleave.middleware.rpc :refer [defremote]]))


(defremote calculate [qty price tax discount]
  (let [q (parse-integer qty)
        p (parse-double price)
        t (parse-double tax)
        d (parse-double discount)]
    (-> (* q p)
        (* (+ 1 (/ t 100)))
        (- d))))

(defremote email-domain-errors [email]
  (v/email-domain-errors email))

