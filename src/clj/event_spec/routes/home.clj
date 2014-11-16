(ns event-spec.routes.home
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
            (cemerick.friend [workflows :as workflows][credentials :as creds])))

(defn home []
  (layout/common [:h1 "Hello World!"]))


(defresource index
  :available-media-types ["text/html"]
  :exists?
  (fn [context]
    [(io/get-resource "/index.html")
     {::file (file (str (io/resource-path) "/index.html"))}])
  :handle-ok
  (fn [{{{resource :resource} :route-params} :request}]
    (clojure.java.io/input-stream (io/get-resource "/index.html")))
  :last-modified
  (fn [{{{resource :resource} :route-params} :request}]
    (.lastModified (file (str (io/resource-path) "/index.html")))))

(defroutes home-routes
  (GET "/" request index)
  (GET "/test" [] (home)))
