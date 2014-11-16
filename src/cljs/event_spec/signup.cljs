(ns event-spec.signup
  (:require [reagent.core :as reagent :refer [atom]]
            [hiccups.runtime :as hi]
            [domina :as dom]
            [ajax.core :refer [GET POST]]
            [domina :refer [value destroy-children! append! destroy! by-id]]
            [domina.events :refer [listen! current-target raw-event]]))



(def username-a (atom ""))

(def email-a (atom ""))

(def password-a (atom ""))

(def re-password-a (atom ""))

(def arr #{"milos" "janko" })


(defn by-id-my [id]
  (.getElementById js/document id))

(defn check-length [text max-length min-length]
  (if (and  (> (count text)min-length)
            (< (count text)max-length))
    true
    false))

(defn check-exist[arr text]
  (if (contains? arr text)
    false
    true))

(defn check-pass-re[pass1]
  (let [pass2 (value(by-id "password"))]
  (if (= pass1  pass2)
    (reset! re-password-a "Excilent")
    (reset! re-password-a "Bad pasword, not equal"))))

(defn check-pass [password]
  (if (re-matches #"([a-zA-Z0-9!]{6,15})" password)
    (reset! password-a "")
    (reset! password-a "Bad password, length must be beatween 6 and 15")))

(defn check-username [username]
  (if (check-length username 15 0)
    (if (check-exist arr username)
    (reset! username-a "")
    (reset! username-a  (str " Already exist.")))
  (reset! username-a "Length must be beatween 0 and 15."))
  (str @username-a))


(defn check-email[email]
  (if (re-matches #"(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?" email)
    (reset! email-a "")
    (reset! email-a "Bad email address")))





(defn username-input []
  [:input {:type "text" :name "username" :placeholder "Username" :id "username" :required "required"
           :on-change #(check-username (-> % .-target .-value))}])

(defn  email-input []
  [:input {:type "text" :name "email" :placeholder "Email" :id "email" :required "required"
           :on-change #(check-email(-> % .-target .-value))}])

(defn password-input []
  [:input {:type "password" :name "password" :placeholder "Password" :id "password" :required "required"
           :on-change #(check-pass(-> % .-target .-value))}])

(defn re-password-input []
  [:input {:type "password" :name "re-password" :placeholder "Password Confirmation" :id "re-password" :required "required"
           :on-change #(check-pass-re (-> % .-target .-value) )}])

(defn alert []
  (js/alert (value (by-id "username"))))

(defn forma-foo []
 [:div
  [:div {:id "inf"}
  [:div {:id "success"}]
  [:div {:id "error"}]]
 [:form {:class "form-horizontal form-signin-signup" }
   [:p  @username-a]
   [:p (username-input)]
   [:p @email-a]
   [:p (email-input)]
   [:p @password-a]
   [:p (password-input)]
   [:p @re-password-a]
   [:p (re-password-input)]
   [:div {:class "control-group"}
    [:select {:class "form-control" :id "year" :name "year" }
     [:option "-- Choose year --"]
    (for [x (range 1990 2050)]
      [:option (str x)])]]
   [:div {:class "control-group"}
   [:select {:class "form-control" :id "gender" :name "gender"}
    [:option "-- Choose gender --"]
    [:option "Male"]
    [:option "Female"]]]
   [:div {:class "control-group"}
   [:input {:type "text" :name "zip-code" :placeholder "Zip code" :id "zipcode"}]]
   [:div {:class "control-group"}
   [:button {:id "add" :type "submit" :value "Signup" :class "btn btn-primary btn-large" }"Create"]]]])


(defn handler [response]
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
    [:div {:class "alert alert-danger" :role "alert"}
      [:button.close {:data-dismiss "alert", :type "button"} "×"]
      [:h4 "Error!"]
      [:p  (:response response)]])))

(defn add-user[]
  (POST "/add-user" {:format
                     :raw
                     :params {:username (value (by-id-my "username"))
                              :email (value (by-id-my "email"))
                              :password (value (by-id-my "password"))
                              :password-re (value (by-id-my "re-password"))
                              :year (value (by-id-my "year"))
                              :gender (value (by-id-my "gender"))
                              :zipcode (value (by-id-my "zipcode"))}
                     :handler handler
                     :error-handler err-handler}))



(defn ^:export run []
  (reagent/render-component
   [forma-foo]
   (by-id-my "forma-foo"))
  (listen! (by-id-my "add") :click add-user))











