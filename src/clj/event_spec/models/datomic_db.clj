(ns event-spec.models.datomic-db
  (:require [datomic.api :as d]
            [cemerick.friend.credentials :as creds]))


(def conn-uri "datomic:free://localhost:4334/event-spec")



;; kreiranje baze

(d/create-database conn-uri)

;; konekcija na bazu

(def conn (d/connect conn-uri))

(def schema (load-file "resources/public/datomic/schema.dtm"))

(d/transact conn schema)

;;kreiranje seme

;(defn create-schema [] (.get (.transact conn
 ;                  (first (Util/readAll
  ;                          (FileReader."schema.dtm"))))))


(defn insert-user [username email password gender year-of-birth zip-cod user-type] (.get (.transact conn
                                [{
                               :db/id #db/id[:db.part/user -1000001]
                               :user/username username
                               :user/email email
                               :user/password (creds/hash-bcrypt password)
                               :user/gender gender
                               :user/yearOfBirth year-of-birth
                               :user/zipCode zip-cod
                               :user/type user-type}])))



(defn user-query []
  (let[temp (d/q '[:find ?u ?p
             :where [?user :user/username ?u]
                    [?user :user/password ?p]
             ]
            (d/db conn))]
  (def users (into {} (map (fn [[k v]] [k {:username k :password v}]) temp)))) users)




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

(defn insert-event [event-name event-type  address price capacity event-time date]
  (.get (.transact conn
                   [{:db/id #db/id[:db.part/user -1000001]
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






