(ns event-spec.edit
  (:require [reagent.core :as reagent :refer [atom]]
            [hiccups.runtime :as hi]
            [domina :as dom]
            [ajax.core :refer [GET POST]]
            [domina :refer [value destroy-children! append! destroy! by-id]]
            [domina.events :refer [listen! current-target raw-event]]))





(defn edit [{:keys [username  email gender yearOfBirth zipCode type ]}]
  [:div
   [:div {:class "span6 offset3"}
    [:form
    [:h4 {:class "widget-header"}
     [:i.icon-cog] "  Edit user " username]
    [:div {:class "widget-body" }
     [:div {:id "success"}]
      [:div {:id "error"}]
     [:div {:class "center-align"}

      [:div.col-md-3.col-lg-3
       {:align "center"}
       [:img.img-circle
        {:src
         "https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=100",
         :alt "User Pic"}]
       [:div.form-group
        [:label {:for "exampleInputFile"} "File input"]
        [:input#exampleInputFile {:type "file"}]]
       [:br]
       [:div.col-md-9.col-lg-9
        [:table.table.table-user-information
         [:tbody
          [:tr
           [:td "Username"]
           [:td [:input {:type "text" :value username :id "username" :name "username" :readonly "readonly"}]]]
          [:tr
           [:td "Email"]
           [:td [:input {:type "text" :value email :id "email" :name "email"}]]]
          [:tr
           [:td "Old password"]
           [:td [:input {:type "text" :value "" :id "old-password" :name "old-password" }]]]
          [:tr
           [:td "New password"]
           [:td [:input {:type "text" :value "" :id "new-password" :name "new-password" }]]]
          [:tr]
          [:tr
           [:td "Date of Birth"]
           [:td
            [:select {:class "form-control" :id "year" :name "year" }
             [:option  {:seleted "selected"} yearOfBirth]
             (for [x (range 1990 2050)]
               [:option (str x)])]]]
          [:tr]
          [:tr
           [:td "Zip code"]
           [:td [:input {:type "text" :value zipCode :id "zipcode" :name "zidcode"}]]]
          [:tr
           [:td "Gender"]
           [:td [:select {:class "form-control" :id "gender" :name "gender"}
                 [:option {:seleted "selected"} gender]
                 [:option "Male"]
                 [:option "Female"]]]]
          [:tr [:td "User type"] [:td [:input {:type "text" :value type :id "type" :name "type" :disabled "disabled"}]]]]]]]]]
    [:div.panel-footer
     [:span.pull-right
      [:button  {:type "submit" :id "edit-user" :value "save" :class "btn btn-primary btn-large" }[:i.icon-edit]" Save changes"]]]]]])

(defn handler [response]
  (append!
   (by-id "edit")
   (hi/render-html (edit (first response)))))

(defn handler-success [response]
    (dom/prepend!
     (dom/by-id "success")
     (hi/render-html
      [:div#success.alert.alert-success
      [:button.close
       {:data-dismiss "alert", :type "button"} "×"]
      [:h4 "Success!"]
      [:p response]])))

(defn err-handler [response]
  (dom/prepend!
   (dom/by-id "error")
   (hi/render-html
    [:div {:id "errorr":class "alert alert-danger" :role "alert"}
      [:button.close {:data-dismiss "alert", :type "button"} "×"]
      [:h4 "Error!"]
      [:p  (:response response)]])))

(defn edit-user []
  (destroy! (by-id "errorr"))
  (POST "/edit-user"
        {:format
         :raw
         :params {:username (value (by-id "username"))
                  :email (value (by-id "email"))
                  :old-password (value (by-id "old-password"))
                  :new-password (value (by-id "new-password"))
                  :year (value (by-id "year"))
                  :gender (value (by-id "gender"))
                  :zipcode (value (by-id "zipcode"))}
         :handler handler-success
         :error-handler err-handler}))

(defn edit-user2[]
  (js/alert "aaa"))

(defn ^:export run []
  (GET "/user" {:handler handler})
  (listen! (by-id "edit-user") :click edit-user))
