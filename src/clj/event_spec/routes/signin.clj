(ns event-spec.routes.signin
  (:require [compojure.core :refer :all]
            [event-spec.views.layout :as layout]
            [liberator.core
             :refer [defresource resource request-method-in]]
            [hiccup.page :refer [html5 include-css]]
            [ring.util.response :as resp]
            [cheshire.core :refer :all]
            [noir.io :as io]
            [noir.response :as response]
            ;[noir.request :as noreq]
            [clojure.java.io :refer [file]]
            [cemerick.friend :as friend]
            [compojure.route :as route]
            [cemerick.friend.credentials :as creds]
            [event-spec.validation :as valid]
            [event-spec.models.datomic-db :as db]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

(defresource signin
  :available-media-types ["text/html"]
  :exists?
  (fn [context]
    [(io/get-resource "/signin.html")
     {::file (file (str (io/resource-path) "/signin.html"))}])
  :handle-ok
  (fn [{{{resource :resource} :route-params} :request}]
    (clojure.java.io/input-stream (io/get-resource "/signin.html")))
  :last-modified
  (fn [{{{resource :resource} :route-params} :request}]
    (.lastModified (file (str (io/resource-path) "/signin.html")))))


(defroutes signin-routes
  (GET "/signin" request signin)
  (GET "/login" request (ring.util.response/redirect "/"))
  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/")))
  (GET "/logged-user" [] (response/edn (:username(cemerick.friend/current-authentication)))))
