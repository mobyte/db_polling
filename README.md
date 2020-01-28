Warning! The project is just for fun and not meant to be used anywhere.

## Features

* Represents most common db poll pattern: 
 1. Manager reads and processes new records.
 3. Move processed records to archive.
 4. Errors increase error counters in certain tables (can be more than one).
 5. When errors counter reaches limit it blocks the record.
 6. Each error is handling by handler fn.
* Implemented as library. So it's easy to use. Several poll managers can be used in one application with different configs.
* Each manager starts as parallel thread without main thread blocking.
* Buffered db reading.
* Multi thread new records processing.

## Examples

Define some config props:

```clj
(defn- register [event]
  (println "registering event: " event))

(defn- logger-error [& messages]
  (apply println "error: " messages))

(defn- logger-debug [& messages]
  (apply println "debug: " messages))

;;;; db spec

(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname (str "//localhost:3306/notify")
         :user "notify"
         :password "notify"})

;;;; error counting and locking tables (can be more than one)

(def lock-table {:table table-events
                 :id-field events-id
                 :err-count-field events-err_count
                 :locked-field events-locked
                 :error-limit 2
                 :calc-id-fn #(get-field-from-row % events-id)
                 })

;;;; db poll table

(def table-info {:table table-events
                 :arc-table table-events_arc
                 :field-id events-id
                 :field-state events-state
                 :field-locked events-locked
                 :state-init states/name-free-id
                 :state-inprogress states/name-inprogress-id
                 :state-done states/name-done-id
                 :fields [events-date 	events-err_count 	events-ext_id events-id
                          events-locked 	events-state 	events-type events-value]
                 :select-joins nil
                 :select-add-filter nil
                 })

;;;; putting all together

(def info {:db db
           :table-info table-info
           :lock-infos [lock-table]
           :processor-fn register
           :error-logger logger-error
           :debug-logger logger-debug
           })
```

Let's try it!

```clj
user> (require '[libs.dbpoll.manager :as manager])
user> (require '[libs.dbpoll.binds :as binds])
user> (binding [binds/info info] (manager/start-manager)
... Detached process output is here. REPL is available ...
user> (binding [binds/info info] (manager/stop-manager)
```