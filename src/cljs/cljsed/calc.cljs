(ns cljsed.calc
  (:require-macros [hiccups.core :as h])
  (:require [domina :refer [by-id value by-class set-value! append! destroy!]]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :refer [listen! prevent-default]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [cljs.reader :refer [read-string]]))

(defn calculate [e]
  (let [qty (value (by-id "quantity"))
        price (value (by-id "price"))
        tax (value (by-id "tax"))
        discount (value (by-id "discount"))]
    (remote-callback :calculate [qty price tax discount]
                     #(set-value! (by-id "total") (.toFixed % 2)))
    (prevent-default e)))

(defn add-help []
  (append! (by-id "shoppingForm")
           (h/html [:div.help "Click to calculate"])))

(defn remove-help []
  (destroy! (by-class "help")))

(defn ^:export init []
  (when (and js/document
           (.-getElementById js/document))
    (listen! (by-id "calc") :click (fn [e] (calculate e)))
    (listen! (by-id "calc") :mouseover add-help)
    (listen! (by-id "calc") :mouseout remove-help)))
