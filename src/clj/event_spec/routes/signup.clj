(ns event-spec.routes.signup
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
            [event-spec.util.validation :as valid]
            [event-spec.models.datomic-db :as db]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

(def users (atom (db/get-all-username)) )

@users

(defn add-in-list[pom]
  (swap! users conj pom))

(defresource signup
  :available-media-types ["text/html"]
  :exists?
  (fn [context]
    [(io/get-resource "/signup.html")
     {::file (file (str (io/resource-path) "/signup.html"))}])
  :handle-ok
  (fn [{{{resource :resource} :route-params} :request}]
    (clojure.java.io/input-stream (io/get-resource "/signup.html")))
  :last-modified
  (fn [{{{resource :resource} :route-params} :request}]
    (.lastModified (file (str (io/resource-path) "/signup.html")))))

(defresource add-user
  :allowed-methods [:post]
  :available-media-types ["text/html"]
  :malformed? (fn [context]
                (let [{:keys [username email password password-re]} (get-in context [:request :params])]
                (valid/check-signup-form username 5 15 email password password-re)))
  :handle-malformed "Bad-form"
  :post! (fn [ctx]
                 (let [{:keys [username email password  gender year zipcode]} (get-in ctx [:request :params])]
                 (do(println "ok")
                   (db/insert-user username email password gender year zipcode "user"))))
  :handle-ok (fn [ctx] "ok" ))



(defroutes signup-routes
  (GET "/signup" request signup)
  (POST "/add-user" request add-user))
