(ns event-spec.models.datomic-db
  (:require [datomic.api :as d]
            [cemerick.friend.credentials :as creds]))


(def conn-uri "datomic:free://localhost:4334/event-spec")



;; kreiranje baze

(d/create-database conn-uri)

;; konekcija na bazu

(def conn (d/connect conn-uri))

(def schema (load-file "resources/public/datomic/schema.edn"))


(d/transact conn schema)

;;kreiranje seme

;(defn create-schema [] (.get (.transact conn
 ;                  (first (Util/readAll
  ;                          (FileReader."schema.dtm"))))))


(defn insert-user [username email password gender year-of-birth zip-cod user-type]
  (.get (.transact conn[{
                               :db/id #db/id [:db.part/user -1000001]
                               :user/username username
                               :user/email email
                               :user/password (creds/hash-bcrypt password)
                               :user/gender gender
                               :user/yearOfBirth year-of-birth
                               :user/zipCode zip-cod
                               :user/type user-type}])))


(defn find-id-for-update[username]
  "return id for update user "
  (ffirst(d/q '[:find ?id
              :in $ ?usename
              :where [?id :user/username ?usename]] (d/db conn) username)))

(defn update [username email gender year zipcode]
  "update user "
  (d/transact conn [{:db/id (find-id-for-update username)
                   :user/email email
                   :user/gender gender
                   :user/yearOfBirth year
                   :user/zipCode zipcode}]))





(defn username-exists? [username]
  (if
    (empty? (d/q '[:find ?u
                   :in $ ?u
                   :where [?e :user/username ?u]] (d/db conn) username ))
    false
    true))


(defn user-query []
  (let[temp (d/q '[:find ?u ?p ?t
             :where [?user :user/username ?u]
                    [?user :user/password ?p]
                   [?user :user/type ?t]
             ]
            (d/db conn))]
   (def users (into {} (map (fn [[k v t]] [k {:username k :password v :roles #{(read-string (str ":"t))}}])temp )))users))


(defn  get-all-username[]
  (d/q '[:find ?u ?p ?t
         :where [?user :user/username ?u]
         [?user :user/password ?p]
         [?user :user/type ?t]]
       (d/db conn)))





(defn get-all-user []
  (let [temp-user
        (d/q '[:find ?username  ?email ?gender ?year ?zipCode ?type
             :where [?user :user/username ?username]
                    [?user :user/email ?email]
                    [?user :user/gender ?gender]
                    [?user :user/yearOfBirth ?year]
                    [?user :user/zipCode ?zipCode]
                    [?user :user/type ?type]]
            (d/db conn))]
        (def  users (into [] (map (fn [[u  e g y z t]]
                                    {:username u :email e :gender g  :yearOfBirth y  :zipCode z :type t }) temp-user)))) users)

(defn get-user-by-username [username]
  (let [temp-user
        (d/q '[:find ?username  ?email ?gender ?year ?zipCode ?type
             :in $ ?username
             :where [?user :user/username ?username]
                    [?user :user/email ?email]
                    [?user :user/gender ?gender]
                    [?user :user/yearOfBirth ?year]
                    [?user :user/zipCode ?zipCode]
                    [?user :user/type ?type]]
            (d/db conn)username)]
        (def  users (into [] (map (fn [[u  e g y z t]]
                                   :key-word u {:username u :email e :gender g  :yearOfBirth y  :zipCode z :type t }) temp-user)))) users)

(defn get-user-username [username]
        (d/q '[:find ?username  ?email ?gender ?year ?zipCode ?type
             :in $ ?username
             :where [?user :user/username ?username]
                    [?user :user/email ?email]
                    [?user :user/gender ?gender]
                    [?user :user/yearOfBirth ?year]
                    [?user :user/zipCode ?zipCode]
                    [?user :user/type ?type]]
            (d/db conn)username))


(defn insert-event [event-name event-type  address price capacity event-time date]
  (.get (.transact conn
                   [{:db/id #db/id[:db.part/user -1000002]
                     :event/eventname event-name
                     :event/type event-type
                     :event/address address
                     :event/price price
                     :event/capacity capacity
                     :event/time event-time
                     :event/date date}])))

(defn get-all-event[]
  (let [temp-events
        (d/q '[:find  ?event-name ?type ?address ?price ?capacity ?event-time ?date
         :where [?event :event/eventname ?event-name]
                [?event :event/type ?type]
                [?event :event/address ?address]
                [?event :event/price ?price]
                [?event :event/capacity ?capacity]
                [?event :event/time ?event-time]
                [?event :event/date ?date]]
       (d/db conn))]
    (def events (into [] (map (fn [[n t a p c e d]]
                               {:event-name n :type t :address a :price p
                                :capacity c :event-time e :date d })
                             temp-events))))events)



(defn get-event-by-type[type]
  (let [temp-events
        (d/q '[:find  ?event-name ?type ?address ?price ?capacity ?event-time ?date
               :in $ ?type
               :where [?event :event/eventname ?event-name]
                [?event :event/type ?type]
                [?event :event/address ?address]
                [?event :event/price ?price]
                [?event :event/capacity ?capacity]
                [?event :event/time ?event-time]
                [?event :event/date ?date]]
       (d/db conn)type)]
    (def events (into [] (map (fn [[n t a p c e d]]
                               {:event-name n :type t :address a :price p
                                :capacity c :event-time e :date d })
                             temp-events))))events)






