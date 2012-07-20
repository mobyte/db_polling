(ns procs.tester
  (:require [libs.dbpoll.manager :as manager]
            [libs.dbpoll.binds :as binds]
            [db.states :as states])
  (:use [libs.db.query]
        [db.entities-q]))

(defn- register [event]
  (/ 1 0)
  (println "registering event: " event))

(defn- logger-error [& messages]
  (apply println "error: " messages))

(defn- logger-debug [& messages]
  (apply println "debug: " messages))

;;;; events

(def db {:classname "com.mysql.jdbc.Driver"
                          :subprotocol "mysql"
                          :subname (str "//localhost:3306/aggregation_system")
                          :user "aggregsys"
                          :password "aggregsys"})

(def lock-table-subs {:table table-subscribers
                      :id-field subscribers-id
                      :err-count-field subscribers-err_count
                      :locked-field subscribers-locked
                      :error-limit 3
                      :calc-id-fn #(get-field-from-row % queue-subscriber)
                      })

(def lock-table-queue {:table table-queue
                      :id-field queue-id
                      :err-count-field queue-err_count
                      :locked-field queue-locked
                      :error-limit 2
                      :calc-id-fn #(get-field-from-row % queue-id)
                      })

(def table-info {:table table-queue
                 :arc-table table-queue_arc
                 :field-id queue-id
                 :field-state queue-state
                 :field-locked queue-locked
                 :state-init states/name-free-id
                 :state-inprogress states/name-inprogress-id
                 :state-done states/name-done-id
                 :fields [queue-id queue-date queue-err_count queue-ext_id queue-locked queue-state queue-subscriber queue-type queue-value]
                 :select-joins [[table-subscribers [:= queue-subscriber subscribers-id]]]
                 :select-add-filter [:= 0 subscribers-locked]
                 })

(def info {:db db
           :table-info table-info
           :lock-infos [lock-table-queue lock-table-subs]
           :processor-fn register
           :processor-error-fn (fn [item message]
                                 (println "my error processor!!!:")
                                 (println "item: " item)
                                 (println "message: " message))
           :error-logger logger-error
           :debug-logger logger-debug
           })

