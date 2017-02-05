(ns cljsed.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [cljsed.templates.calc :refer [calc]]
            [cljsed.login :refer [authenticate-user]]
            [shoreleave.middleware.rpc :refer [wrap-rpc]]))


(defroutes app-routes
  (GET "/" [] "Hello")
  (POST "/login" [email password] (authenticate-user email password))
  (POST "/calc" [quantity price tax discount]
        (calc quantity price tax discount))
  (resources "/")
  (not-found "Page not found"))

;; site function create an handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:
(def handler
  (site app-routes))

(def app (-> (var handler)
             (wrap-rpc)
             (site)))
