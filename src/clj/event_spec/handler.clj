(ns event-spec.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [event-spec.routes.home :refer [home-routes]]
            [event-spec.routes.signup :refer [signup-routes]]))

(defn init []
  (println "event-spec is starting"))

(defn destroy []
  (println "event-spec is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found1"))

(def app
  (-> (routes home-routes signup-routes app-routes)
      (handler/site)
      (wrap-base-url)))


