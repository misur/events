(ns event-spec.routes.profile
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
            [event-spec.models.datomic-db :as db]))

(defresource profile
  :available-media-types ["text/html"]
  :exists?
  (fn [context]
    [(io/get-resource "/profile.html")
     {::file (file (str (io/resource-path) "/profile.html"))}])
  :handle-ok
  (fn [{{{resource :resource} :route-params} :request}]
    (clojure.java.io/input-stream (io/get-resource "/profile.html")))
  :last-modified
  (fn [{{{resource :resource} :route-params} :request}]
    (.lastModified (file (str (io/resource-path) "/profile.html")))))



(defroutes profile-routes
  (GET "/user" request (response/edn (db/get-user-by-username (:username(cemerick.friend/current-authentication)))))
  (GET "/profile" request
       (friend/authorize #{:user} profile)))

