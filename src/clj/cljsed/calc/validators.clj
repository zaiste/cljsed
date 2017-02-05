(ns cljsed.calc.validators
  (:require [valip.core :refer [validate]]
            [valip.predicates :refer [present? integer-string? decimal-string?
                                      gt]]))

(defn validate-calc-form [quantity price tax discount]
  (validate {:quantity quantity :price price :tax tax :discount discount}
            ;; presence
            [:quantity present? "Quantity cannot be empty"]
            [:price present? "Price cannot be empty"]
            [:tax present? "Tax cannot be empty"]
            [:discount present? "Discount cannot be empty"]

            ;; type
            [:quantity integer-string? "Quantity has to be an integer"]
            [:price decimal-string? "Price has to be a decimal"]
            [:tax decimal-string? "Tax has to be a decimal"]
            [:discount decimal-string? "Discount has to be a decimal"]

            ;; range
            [:quantity (gt 0) "Quantity cannot be negative"]))
