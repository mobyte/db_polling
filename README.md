Warning! The library is not for common use. It was created just for a couple of my current projects.

## Features

* Implemented as library.
* Easy to use.
* Represents most common db poll pattern.
* Multi thread processing.
* Error counting and locking table entries

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
...  ...
user> (binding [binds/info info] (manager/stop-manager)
```