(ns event-spec.signin
  (:require [reagent.core :as reagent :refer [atom]]
            [hiccups.runtime :as hi]
            [domina :as dom]
            [ajax.core :refer [GET POST]]
            [domina :refer [value destroy-children! append! destroy! by-id]]
            [domina.events :refer [listen! current-target raw-event]]))

(def checkbox(atom "password"))


(defn change-value []
  (if (= @checkbox "password")
    (reset! checkbox "text")
    (reset! checkbox "password")))


(defn forma[]
  [:div
   [:form {:class "form-horizontal form-signin-signup" :role "form" :method "post" :action "/login"}
    [:input {:type "text" :name "username" :placeholder "Username" :id "username" :required "required"
             :on-change #()}]
    [:input {:type @checkbox :name "password" :placeholder "Password" :id "password" :required "required"
             :on-change #()}]
    [:div {:class ""}
     [:label [:input {:type "checkbox" :on-change #(change-value)}" Show password"]]]
    [:div {:class "control-group"}
     [:button {:id "signin" :type "submit" :value "Signup" :class "btn btn-primary btn-large" }"Signin"]]]])






(defn ^:export run []
  (reagent/render-component
   [forma]
   (by-id "forma")))
