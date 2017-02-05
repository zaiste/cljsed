(ns cljsed.calc.validators-test
  (:require [clojure.test :refer [deftest are testing]]
            [cljsed.calc.validators :refer [validate-calc-form]]))

(deftest validate-calc-form-test
  (testing "Calc Validation"
    (testing "/ Happy Path"
      (are [expected actual] (= expected actual)
           nil (validate-calc-form "1" "0" "0" "0")
           nil (validate-calc-form "1" "0.0" "0.0" "0.0")
           nil (validate-calc-form "10" "10.25" "10.25" "99.99")))

    (testing "/ No Presence"
      (are [expected actual] (= expected actual)

           "Quantity cannot be empty"
           (first (:quantity (validate-calc-form "" "0" "0" "0")))

           "Price cannot be empty"
           (first (:price (validate-calc-form "1" "" "0" "0")))

           "Tax cannot be empty"
           (first (:tax (validate-calc-form "1" "0" "" "0")))

           "Discount cannot be empty"
           (first (:discount (validate-calc-form "1" "0" "0" "")))))

    (testing "/ Value Type"
      (are [expected actual] (= expected actual)

           "Quantity has to be an integer"
           (first (:quantity (validate-calc-form "foo" "0" "0" "0")))

           "Quantity has to be an integer"
           (first (:quantity (validate-calc-form "1.1" "0" "0" "0")))

           "Price has to be a decimal"
           (first (:price (validate-calc-form "1" "foo" "0" "0")))

           "Tax has to be a decimal"
           (first (:tax (validate-calc-form "1" "0" "foo" "0")))

           "Discount has to be a decimal"
           (first (:discount (validate-calc-form "1" "0" "0" "foo")))))

    (testing "/ Value Range"
      (are [expected actual] (= expected actual)

           "Quantity cannot be negative"
           (first (:quantity (validate-calc-form "-1" "0" "0" "0")))))))
