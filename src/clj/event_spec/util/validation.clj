(ns event-spec.util.validation
  (:require [event-spec.models.datomic-db :as db]))

(defn check-length [text max-length min-length]
  (if (and  (> (count text)min-length)
            (< (count text)max-length))
    true
    false))

(defn check-exist[text]
  (if (db/username-exists? text)
    false
    true))

(defn check-pass-re[pass1 pass2]
  (if (= pass1  pass2)
    true
    false))

(defn check-pass [password]
  (if (re-matches #"([a-zA-Z0-9!]{6,15})" password)
   true
   false))


(defn check-email[email]
  (if (re-matches #"(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?" email)
    true
    false))

(defn check-username[username min-length max-length]
  (if(and (check-length username max-length min-length)
          (check-exist username))
    true
    false))

(defn check-password [pass1 pass2]
  (if(and(check-pass pass1)
         (check-pass-re pass1 pass2))
    true
    false))


(defn  check-signup-form[username min-length max-length email pass1 pass2]
  (if (and
       (and (check-username username min-length max-length)
           (check-email email))
       (check-password pass1 pass2))
    false
    true))