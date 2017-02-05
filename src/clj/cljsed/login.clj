(ns cljsed.login
  (:require [cljsed.login.validators :refer [user-credentials-errors]]
            [cljsed.login.java.validators :refer [email-domain-errors]]))

(defn authenticate-user [email password]
  (if (or (boolean (user-credentials-errors email password)) (boolean (email-domain-errors email)))
    (str "Please complete the form.")
    (str email " and " password
         " passed the formal validation, but we still have to authenticate you")))
