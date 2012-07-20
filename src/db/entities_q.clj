(ns db.entities-q)

;;;; data_types
(def table-data_types "data_types")
(def data_types-id {:type {:size 10, :name "INT UNSIGNED"}, :table "data_types", :name "id"})
(def data_types-name {:type {:size 16, :name "VARCHAR"}, :table "data_types", :name "name"})

;;;; logs
(def table-logs "logs")
(def logs-id {:type {:size 20, :name "BIGINT UNSIGNED"}, :table "logs", :name "id"})
(def logs-system {:type {:size 255, :name "VARCHAR"}, :table "logs", :name "system"})
(def logs-type {:type {:size 255, :name "VARCHAR"}, :table "logs", :name "type"})
(def logs-ext_id {:type {:size 50, :name "VARCHAR"}, :table "logs", :name "ext_id"})
(def logs-error {:type {:size 4000, :name "VARCHAR"}, :table "logs", :name "error"})
(def logs-date {:type {:size 19, :name "TIMESTAMP"}, :table "logs", :name "date"})

;;;; queue
(def table-queue "queue")
(def queue-id {:type {:size 20, :name "BIGINT UNSIGNED"}, :table "queue", :name "id"})
(def queue-subscriber {:type {:size 10, :name "INT UNSIGNED"}, :table "queue", :name "subscriber"})
(def queue-type {:type {:size 10, :name "INT UNSIGNED"}, :table "queue", :name "type"})
(def queue-ext_id {:type {:size 50, :name "VARCHAR"}, :table "queue", :name "ext_id"})
(def queue-value {:type {:size 8000, :name "VARCHAR"}, :table "queue", :name "value"})
(def queue-state {:type {:size 10, :name "INT UNSIGNED"}, :table "queue", :name "state"})
(def queue-date {:type {:size 19, :name "TIMESTAMP"}, :table "queue", :name "date"})
(def queue-err_count {:type {:size 10, :name "INT"}, :table "queue", :name "err_count"})
(def queue-locked {:type {:size 3, :name "TINYINT"}, :table "queue", :name "locked"})

;;;; queue_arc
(def table-queue_arc "queue_arc")
(def queue_arc-id {:type {:size 20, :name "BIGINT UNSIGNED"}, :table "queue_arc", :name "id"})
(def queue_arc-subscriber {:type {:size 10, :name "INT UNSIGNED"}, :table "queue_arc", :name "subscriber"})
(def queue_arc-type {:type {:size 10, :name "INT UNSIGNED"}, :table "queue_arc", :name "type"})
(def queue_arc-ext_id {:type {:size 50, :name "VARCHAR"}, :table "queue_arc", :name "ext_id"})
(def queue_arc-value {:type {:size 8000, :name "VARCHAR"}, :table "queue_arc", :name "value"})
(def queue_arc-state {:type {:size 10, :name "INT UNSIGNED"}, :table "queue_arc", :name "state"})
(def queue_arc-date {:type {:size 19, :name "TIMESTAMP"}, :table "queue_arc", :name "date"})
(def queue_arc-err_count {:type {:size 10, :name "INT"}, :table "queue_arc", :name "err_count"})
(def queue_arc-locked {:type {:size 3, :name "TINYINT"}, :table "queue_arc", :name "locked"})

;;;; queue_locked
(def table-queue_locked "queue_locked")
(def queue_locked-id {:type {:size 20, :name "BIGINT UNSIGNED"}, :table "queue_locked", :name "id"})
(def queue_locked-subscriber {:type {:size 10, :name "INT UNSIGNED"}, :table "queue_locked", :name "subscriber"})
(def queue_locked-type {:type {:size 10, :name "INT UNSIGNED"}, :table "queue_locked", :name "type"})
(def queue_locked-ext_id {:type {:size 50, :name "VARCHAR"}, :table "queue_locked", :name "ext_id"})
(def queue_locked-value {:type {:size 8000, :name "VARCHAR"}, :table "queue_locked", :name "value"})
(def queue_locked-state {:type {:size 10, :name "INT UNSIGNED"}, :table "queue_locked", :name "state"})
(def queue_locked-date {:type {:size 19, :name "TIMESTAMP"}, :table "queue_locked", :name "date"})
(def queue_locked-err_count {:type {:size 10, :name "INT"}, :table "queue_locked", :name "err_count"})
(def queue_locked-locked {:type {:size 3, :name "TINYINT"}, :table "queue_locked", :name "locked"})

;;;; states
(def table-states "states")
(def states-id {:type {:size 10, :name "INT UNSIGNED"}, :table "states", :name "id"})
(def states-name {:type {:size 64, :name "VARCHAR"}, :table "states", :name "name"})

;;;; subscribers
(def table-subscribers "subscribers")
(def subscribers-id {:type {:size 10, :name "INT UNSIGNED"}, :table "subscribers", :name "id"})
(def subscribers-type {:type {:size 10, :name "INT"}, :table "subscribers", :name "type"})
(def subscribers-system {:type {:size 10, :name "INT UNSIGNED"}, :table "subscribers", :name "system"})
(def subscribers-disabled {:type {:size nil, :name "BIT"}, :table "subscribers", :name "disabled"})
(def subscribers-err_count {:type {:size 10, :name "INT"}, :table "subscribers", :name "err_count"})
(def subscribers-locked {:type {:size 3, :name "TINYINT"}, :table "subscribers", :name "locked"})
(def subscribers-config {:type {:size 10, :name "INT"}, :table "subscribers", :name "config"})

;;;; subscribers_config
(def table-subscribers_config "subscribers_config")
(def subscribers_config-id {:type {:size 10, :name "INT"}, :table "subscribers_config", :name "id"})
(def subscribers_config-error_count {:type {:size 10, :name "INT"}, :table "subscribers_config", :name "error_count"})
(def subscribers_config-check_time {:type {:size 10, :name "INT"}, :table "subscribers_config", :name "check_time"})

;;;; system_config
(def table-system_config "system_config")
(def system_config-id {:type {:size 10, :name "INT"}, :table "system_config", :name "id"})
(def system_config-error_count {:type {:size 10, :name "INT"}, :table "system_config", :name "error_count"})
(def system_config-check_time {:type {:size 10, :name "INT"}, :table "system_config", :name "check_time"})

;;;; systems
(def table-systems "systems")
(def systems-id {:type {:size 10, :name "INT UNSIGNED"}, :table "systems", :name "id"})
(def systems-name {:type {:size 255, :name "VARCHAR"}, :table "systems", :name "name"})
(def systems-locked {:type {:size nil, :name "BIT"}, :table "systems", :name "locked"})
(def systems-err_count {:type {:size 10, :name "INT"}, :table "systems", :name "err_count"})
(def systems-rerun_count {:type {:size 10, :name "INT"}, :table "systems", :name "rerun_count"})
(def systems-url {:type {:size 512, :name "VARCHAR"}, :table "systems", :name "url"})
(def systems-data_type {:type {:size 10, :name "INT UNSIGNED"}, :table "systems", :name "data_type"})
(def systems-disabled {:type {:size nil, :name "BIT"}, :table "systems", :name "disabled"})

;;;; types
(def table-types "types")
(def types-id {:type {:size 10, :name "INT UNSIGNED"}, :table "types", :name "id"})
(def types-name {:type {:size 128, :name "VARCHAR"}, :table "types", :name "name"})

