(ns cljsed.login
  (:require-macros [hiccups.core :refer [html]]
                   [shoreleave.remotes.macros :as shore-macros])
  (:require [domina :refer [by-id by-class value append! prepend! attr log destroy!]]
            [domina.events :refer [listen! prevent-default]]
            [hiccups.runtime :as hiccupsrt]
            [cljsed.login.validators :refer [user-credentials-errors]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]))

(defn validate-email-domain [email]
  (remote-callback :email-domain-errors
                   [email]
                   #(if %
                      (do
                        (prepend! (by-id "loginForm")
                                  (html [:div.help.email "The email domain doesn't exist."]))
                        false)
                      true)))

(defn validate-email [email]
  (log "Enter validate-email")
  (destroy! (by-class "email"))
  (if-let [errors (:email (user-credentials-errors (value email) nil))]
    (do
      (prepend! (by-id "loginForm") (html [:div.help.email (first errors)]))
      false)
    (validate-email-domain (value email))))

(defn validate-password [password]
  (destroy! (by-class "password"))
  (if-let [errors (:password (user-credentials-errors nil (value password)))]
    (do
      (append! (by-id "loginForm") (html [:div.help.password (first errors)]))
      false)
    true))

(defn validate-form [e email password]
  (if-let [{e-errs :email p-errs :password} (user-credentials-errors (value email) (value password))]
    (if (or e-errs p-errs)
      (do
        (destroy! (by-class "help"))
        (prevent-default e)
        (append! (by-id "loginForm") (html [:div.help "Please complete the form"])))
      (prevent-default e))
    true))

;; attach validate-form to onsubmit event of the form
(defn ^:export init []
  ;; verify that js/document exists and that it has
  ;; getElementById property
  (if (and js/document
           (.-getElementById js/document))
    (let [email (by-id "email")
          password (by-id "password")]
      (listen! (by-id "submit") :click (fn [e] (validate-form e email password)))
      (listen! email :blur (fn [e] (validate-email email)))
      (listen! password :blur (fn [e] (validate-password password))))))
