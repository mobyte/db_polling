(ns libs.dbpoll.error
  (:require [clojure.java.jdbc :as jdbc])
  (:use [libs.db.query]
        [libs.dbpoll.binds]))

(def ^:private ^:dynamic lock-table-info nil)

(defn- lock-table [] (:table lock-table-info))
(defn- lock-id-field [] (:id-field lock-table-info))
(defn- lock-err-count-field [] (:err-count-field lock-table-info))
(defn- lock-field [] (:locked-field lock-table-info))
(defn- lock-error-limit [] (:error-limit lock-table-info))
(defn- lock-calc-id [event] ((:calc-id-fn lock-table-info) event))

(defn- get-error-count-db [id]
  (first (with-db (db) (select (lock-table)
                               :where [:= id (lock-id-field)]))))

(defn- get-error-count [id]
  (if-let [result (get-field-from-row (get-error-count-db id)
                                      (lock-err-count-field))]
    result 0))

(defn- inc-error-count [id]
  (with-db (db)
    (jdbc/do-commands
     (str "update " (lock-table)
          " set " (:name (lock-err-count-field)) " = " (:name (lock-err-count-field)) "+1"
          " where id = " id))))

(defn- reset-count [event]
  (with-db (db) (update (lock-table) {(lock-err-count-field) 0}
                        [:= (lock-calc-id event) (lock-id-field)])))

(defn- limit-exceed? [err-count]
  (>= err-count (lock-error-limit)))

(defn- lock [id]
  (with-db (db) (update (lock-table)
                        {(lock-field) 1}
                        [:= id (lock-id-field)])))

(defn- process-error [event]
  (let [id (lock-calc-id event)]
    (inc-error-count id)
    (if (limit-exceed? (get-error-count id))
      (lock id))))

;;;; register error

(defn register-error [event lock-infos]
  (doseq [info lock-infos]
    (binding [lock-table-info info]
      (process-error event))))

(defn reset-error-count [event lock-infos]
  (doseq [info lock-infos]
    (binding [lock-table-info info]
      (reset-count event))))




