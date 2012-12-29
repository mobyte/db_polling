(ns libs.dbpoll.binds
  (:use libs.dbpoll.glib))


(def lock-table-info-example {:table nil ;table-subscribers
                              :id-field nil ;subscribers-id
                              :err-count-field nil ;subscribers-err_count
                              :locked-field nil ;subscribers-locked
                              :error-limit nil ;3
                              :calc-id-fn nil ;#(get-field-from-row % queue-subscriber)
                              :lock-error-fn #(println "Damn! The item is locked!!! " %)
                              })

(def lock-table-info-example2 {:table nil ;table-queue
                               :id-field nil ;queue-id
                               :err-count-field nil ;queue-err_count
                               :locked-field nil ;queue-locked
                               :error-limit nil ;2
                               :calc-id-fn nil ;#(get-field-from-row % queue-id)
                               })

(def table-info-example {:table nil ;table-queue
                         :arc-table nil ;table-queue_arc
                         :field-id nil ;queue-id
                         :field-state nil ;queue-state
                         :field-locked nil ;queue-locked
                         :state-init nil ;states/name-free-id
                         :state-inprogress nil ;states/name-inprogress-id
                         :state-done nil ;states/name-done-id
                         :fields nil ;[queue-id queue-date queue-err_count queue-ext_id queue-locked queue-state queue-subscriber queue-type queue-value]
                         :select-joins nil ;[[table-subscribers [:= queue-subscriber subscribers-id]]]
                         :select-add-filter nil ;[:= 0 subscribers-locked]
                         })

(def info-example {:manager-num nil ;; 1
                   :db nil ;;conf/db
                   :table-info nil ;table-info-queue
                   :lock-infos nil ;[lock-table-info-queue lock-table-info-subs]
                   :processor-fn nil ;;register/send-event-to-subscriber
                   :processor-error-fn nil ; (fn [item message] ... processor-fn error handler ...)
                   :error-logger nil ;;logger/error
                   :debug-logger nil ;;logger/debug
                   })

(def ^:dynamic info nil)

(defn manager-num [] (:manager-num info))
(defn db [] (:db info))
(defn table-info [] (:table-info info))
(defn lock-infos [] (:lock-infos info))
(defn processor [] (:processor-fn info))

(def ^:private processor-error-default (fn [item message]
                                         (println "processor error:")
                                         (println "item: " item)
                                         (println "message: " message)))

(defn processor-error [] (if-let [error-fn (:processor-error-fn info)]
                           error-fn
                           processor-error-default))

(defn thread-pool [] (or (:thread-pool info) 10))
(defn thread-full-sleep-time [] (or (:thread-full-sleep-time info) 10))

(defn processor-exception [] (fn [item e]
                               ((processor-error) item (stacktrace-to-string e))))

(defn error-logger [] (:error-logger info))
(defn debug-logger [] (:debug-logger info))

(defn table [] (:table (table-info)))
(defn arc-table [] (:arc-table (table-info)))
(defn field-id [] (:field-id (table-info)))
(defn field-state [] (:field-state (table-info)))
(defn field-locked [] (:field-locked (table-info)))
(defn fields [] (:fields (table-info)))
(defn state-init [] (:state-init (table-info)))
(defn state-inprogress [] (:state-inprogress (table-info)))
(defn state-done [] (:state-done (table-info)))
(defn select-joins [] (:select-joins (table-info)))
(defn select-filter [] (:select-add-filter (table-info)))
