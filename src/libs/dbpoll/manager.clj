(ns libs.dbpoll.manager
  (:require [clojure.java.jdbc :as jdbc]
            [libs.dbpoll.error :as error])
  (:use [libs.db.query]
        [libs.dbpoll.binds]
        [libs.dbpoll.glib]))


(def ^:dynamic ^:private tasks (ref {}))

;;;; (un)register process

(defn- register-process [ref]
  (alter tasks
         #(assoc % ref "running")))

(defn- unregister-process [ref]
  (dosync (alter tasks
                 #(dissoc % ref))))

;;;; set event states

(defn- set-item-state [id state]
  (with-db (db) (update (table)
                        {(field-state) state}
                        [:= id (field-id)])))

(defn- set-item-state-free [id]
  (set-item-state id (state-init)))

(defn- set-item-state-inprogress [id]
  (set-item-state id (state-inprogress)))

(defn- set-item-state-done [id]
  (set-item-state id (state-done)))

;;;; save to archive

(defn- make-row-getter [item]
  (fn [field] (get-field-from-row item field)))

(defn- move-done-item-to-archive [item]
  (with-db (db)
    (let [actual-item (first (select (table)
                                     :where [:= (field-id)
                                             (get-field-from-row item
                                                                 (field-id))]))
          getter (make-row-getter actual-item)]
      (insert (arc-table)
              (fields)
              (map getter (fields)))
      (jdbc/delete-rows (table) ["id=?" (getter (field-id))]))))

;;;; async process

(defn- get-current-thread-id []
  (.getName (Thread/currentThread)))

(defn- invoke-process [item]
  "Пытается отправить событие подписчику"
  (let [item-id (get-field-from-row item (field-id))]
    (set-item-state-inprogress item-id)
    (try ((processor) item)
         (error/reset-error-count item (lock-infos))
         (set-item-state-done item-id)
	 (move-done-item-to-archive item)
         (catch Exception ex
           (try
             ((processor-exception) item ex)
             (error/register-error item (lock-infos))
             (set-item-state-free item-id)
             (catch Exception e (println "invoke-process exception processing error: "
                                         "item: " item
                                         "exception: " (stacktrace-to-string e))))))
    (unregister-process item-id)))

;;;; thread safe get next item

(def ^{:private true} items-buffer (ref #{}))

(defn- get-next-items-db []
  (select-with-db (db) (table)
    :fields (fields)
    :join (select-joins)
    :where [:and
            [:and
             [:= 0 (field-locked)]
             (if-let [filter (select-filter)] filter [:= 1 1])]
            [:and
             [:= (state-init) (field-state)]
             (if (empty? @tasks)
               [:= 1 1]
               [:not [:in (field-id) (into [] (keys @tasks))]])]]
    :order [(field-id)]
    :limit 2000))

(defn- fill-item-buffer []
  (when (empty? @items-buffer)
    (ref-set items-buffer (into #{} (concat (get-next-items-db)
                                             @items-buffer)))))

(defn- get-next-item-from-buffer []
  (fill-item-buffer)
  (let [item (first @items-buffer)]
    (alter items-buffer rest)
    item))

(defn- get-next-item []
  (dosync (let [item (get-next-item-from-buffer)
                item-id (get-field-from-row item (field-id))]
            (when-not (or (nil? item)
                          (@tasks item-id))
              (register-process item-id)
              item))))

;;;; item processor

(def ^:private sleep-time 2000)
(def ^:private thread-sleep-time 10)
(def ^:private running? (atom {}))

(defn- set-running-state []
  (ref-set (@running? (manager-num)) true))

(defn- set-stopped-state []
  (dosync (ref-set (@running? (manager-num)) false)))

(defn- run? [] (if-let [running? (@running? (manager-num))]
                 (deref running?)
                 false))

(defn- manager-num-str [] (str "Manager #" (manager-num) ": "))

(defn- printval [& text]
  (let [text (apply str (manager-num-str) text)]
   ((debug-logger) text)
   text))

(defn- manager []
  (try
    (if (< (count @tasks) (thread-pool))
      (if-let [item (get-next-item)]
        (do (printval "peek item: "
             (get-field-from-row item (field-id)))
            (future (invoke-process item)))
        (do (printval "There is no process in queue. Sleeping "
             sleep-time " msecs ...")
            (Thread/sleep sleep-time)))
      (do (printval "Thread pool is full. Sleeping "
           (thread-full-sleep-time) " msecs ...")
          (Thread/sleep (thread-full-sleep-time))))
    (catch Exception e
      ((error-logger) (stacktrace-to-string e))
      (throw e)))
  (if (run?)
    (recur)))

(defn start-manager []
  (dosync (if (run?)
            (printval "Manager already running")
            (do (binding [tasks (ref {})]
                  (swap! running? assoc (manager-num) (ref false))
                  (set-running-state)
                  (future (manager))
                  (printval "Manager started!"))))))

(defn- clear-tasks [] (dosync (ref-set tasks {})))

(defn stop-manager []
  (set-stopped-state)
  (clear-tasks)
  (printval "Set STOP status"))
