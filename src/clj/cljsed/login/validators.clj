(ns cljsed.login.validators
  (:require [valip.core :refer [validate]]
            [valip.predicates :refer [present? matches email-address?]]))

(def ^:dynamic *re-password* #"^(?=.*\d).{4,8}$")

(defn user-credentials-errors [email password]
  (validate {:email email :password password}
            [:email present? "Email can't be empty"]
            [:email email-address? "Provided email is invalid"]
            [:password present? "Password can't be empty"]
            [:password (matches *re-password*) "Provided password is invalid"]))
