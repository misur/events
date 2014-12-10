(ns event-spec.routes.edit
  (:require [compojure.core :refer :all]
            [event-spec.views.layout :as layout]
            [liberator.core
             :refer [defresource resource request-method-in]]
            [hiccup.page :refer [html5 include-css]]
            [ring.util.response :as resp]
            [cheshire.core :refer :all]
            [noir.io :as io]
            [noir.response :as response]
            [clojure.java.io :refer [file]]
            [cemerick.friend :as friend]
            [compojure.route :as route]
            [cemerick.friend.credentials :as creds]
            [event-spec.util.resources :as r]
            (cemerick.friend [workflows :as workflows][credentials :as creds])
            [event-spec.models.datomic-db :as db]
            [event-spec.util.validation :as valid]))


(defresource edit
  :available-media-types ["text/html"]
  :exists?
  (fn [context]
    [(io/get-resource "/edit.html")
     {::file (file (str (io/resource-path) "/edit.html"))}])
  :handle-ok
  (fn [{{{resource :resource} :route-params} :request}]
    (clojure.java.io/input-stream (io/get-resource "/edit.html")))
  :last-modified
  (fn [{{{resource :resource} :route-params} :request}]
    (.lastModified (file (str (io/resource-path) "/edit.html")))))

(defresource edit-user
  :allowed-methods [:post]
  :available-media-types ["text/html"]
  :malformed? (fn [context]
                (let [{:keys [username email old-password new-password]} (get-in context [:request :params])]
                (empty? email)))
  :handle-malformed "Bad-form"
  :post! (fn [ctx]
                 (let [{:keys [username]} (get-in ctx [:request :params])]
                 (println "ok")))
  :handle-ok (fn [ctx] "ok" ))


(defroutes edit-routes
  (POST "/edit-user" request edit-user)
  (GET "/user" request (response/edn (db/get-user-by-username (:username(cemerick.friend/current-authentication)))))
  (GET "/edit" [id]
       (friend/authorize #{:user} edit)))
