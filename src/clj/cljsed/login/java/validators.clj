(ns cljsed.login.java.validators
  (:require [valip.core :refer [validate]]
            [valip.java.predicates :refer [valid-email-domain?]]))

(defn email-domain-errors [email]
  (validate {:email email}
            [:email valid-email-domain? "Domain doesn't exist"]))
