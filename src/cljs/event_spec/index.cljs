(ns event-spec.index
  (:require [reagent.core :as reagent :refer [atom]]
            [hiccups.runtime :as hi]
            [ajax.core :refer [GET POST]]))


(def click-count (atom 0))

(def logged-u (atom nil))

(defn by-id [id]
  (.getElementById js/document id))

(defn counting-component []
  [:div
   "Logged user: " [:code @logged-u]])

(defn currently-logged [logged-user]
 (reset! logged-u logged-user))

(defn ^:export run []
  (reagent/render-component
   [counting-component]
   (by-id "test"))
  (GET "/logged-user" {:handler currently-logged}))


