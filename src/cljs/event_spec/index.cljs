(ns event-spec.index
  (:require [reagent.core :as reagent :refer [atom]]
            [hiccups.runtime :as hi]))


(def click-count (atom 0))

(defn by-id [id]
  (.getElementById js/document id))

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!" :class "btn"
            :on-click #(swap! click-count inc)}]])

(defn ^:export run []
  (reagent/render-component

   [counting-component]
   (by-id "test")))


