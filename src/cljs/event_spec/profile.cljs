(ns event-spec.profile
  (:require [reagent.core :as reagent :refer [atom]]
            [hiccups.runtime :as hi]
            [domina :as dom]
            [ajax.core :refer [GET POST]]
            [domina :refer [value destroy-children! append! destroy! by-id]]
            [domina.events :refer [listen! current-target raw-event]]))



(defn profile [{:keys [username  email gender yearOfBirth zipCode type ]}]
  [:div
   [:div {:class "span6 offset3"}
    [:h4 {:class "widget-header"}
     [:i.icon-user] "User details " username]
    [:div {:class "widget-body" }
     [:div {:class "center-align"}
      [:div.col-md-3.col-lg-3
       {:align "center"}
       [:img.img-circle
        {:src
         "https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=100",
         :alt "User Pic"}]

       [:div.col-md-9.col-lg-9
        [:table.table.table-user-information
         [:tbody
          [:tr [:td "Username"] [:td username]]
         [:tr
           [:td "Email"]
           [:td [:a {:href (str "mailto:" email)} email]]]
          [:tr [:td "Date of Birth"] [:td yearOfBirth]]
          [:tr]
          [:tr [:td "Zip code"] [:td zipCode]]
          [:tr [:td "Gender"] [:td gender]]
          [:tr [:td "User type"] [:td type]]]]]]]]
    [:div.panel-footer
     [:span.pull-right
      [:a.btn.btn-sm.btn-warning
       {:type "button",
        :data-toggle "tooltip",
        :data-original-title "Edit this user",
        :href "edit.html"}
       [:i.icon-edit]]]]]])

(defn handler [response]
  (append!
   (by-id "profile")
   (hi/render-html (profile (first response)))))

(defn error-handler []
  (GET "/"))


(defn ^:export run []

  (GET "/user" {:handler handler
                :error-handler error-handler}))
