(ns cljsed.templates.calc
  (:require [net.cgrand.enlive-html :refer [deftemplate set-attr]]
            [cljsed.remotes :refer [calculate]]))

(deftemplate calc "public/calc.html"
  [quantity price tax discount]
  [:#quantity] (set-attr :value quantity)
  [:#price] (set-attr :value price)
  [:#tax] (set-attr :value tax)
  [:#discount] (set-attr :value discount)
  [:#total] (set-attr :value
                      (format "%.2f" (calculate quantity price tax discount))))
