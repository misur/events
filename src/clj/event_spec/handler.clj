(ns event-spec.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.util.response :as resp]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [event-spec.routes.home :refer [home-routes]]
            [event-spec.routes.signup :refer [signup-routes]]
            [event-spec.models.datomic-db :as db]
            [ring.middleware.params :only [wrap-params]]
            [ring.middleware.keyword-params :only [wrap-keyword-params]]
            [ring.middleware.nested-params :only [wrap-nested-params]]
            [cemerick.friend :as friend]
            [cemerick.friend.credentials :as creds]
            [cemerick.friend.workflows :as workflows]
            [hiccup.page :as h]
            [event-spec.routes.signin :refer [signin-routes]]))

(defn init []
  (println "event-spec is starting"))

(defn destroy []
  (println "event-spec is shutting down"))

(def users
  "dummy in-memory user database."
  {"root" {:username "root"
           :password (creds/hash-bcrypt "admin_password")
           :roles #{:admin}}
   "jane" {:username "jane"
           :password (creds/hash-bcrypt "user_password")
           :roles #{:user}}})

(defroutes app-routes
  (route/resources "/")
  (route/not-found "not found"))

(def my-routes (-> (routes home-routes signup-routes signin-routes app-routes)))

(def app
      (handler/site
      (friend/authenticate
              my-routes

              {:allow-anon? true
               :login-uri "/login"
               :default-landing-uri "/"
               :unauthorized-handler #(-> (h/html5 [:h2 "You do not have sufficient privileges to access " (:uri %)])
                                        resp/response
                                        (resp/status 401))
               :credential-fn #(creds/bcrypt-credential-fn (db/user-query) %)
               :workflows [(workflows/interactive-form)]})))


