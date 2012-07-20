(ns db.entities)

;;;; events
(def table-events "events")
(def events-id {:type {:size 20, :name "BIGINT UNSIGNED"}, :table "events", :name "id"})
(def events-ext_id {:type {:size 50, :name "VARCHAR"}, :table "events", :name "ext_id"})
(def events-type {:type {:size 128, :name "VARCHAR"}, :table "events", :name "type"})
(def events-date {:type {:size 19, :name "TIMESTAMP"}, :table "events", :name "date"})
(def events-value {:type {:size 8192, :name "VARCHAR"}, :table "events", :name "value"})
(def events-state {:type {:size 10, :name "INT"}, :table "events", :name "state"})
(def events-err_count {:type {:size 10, :name "INT"}, :table "events", :name "err_count"})
(def events-locked {:type {:size 1, :name "BIT"}, :table "events", :name "locked"})

;;;; events_arc
(def table-events_arc "events_arc")
(def events_arc-id {:type {:size 20, :name "BIGINT UNSIGNED"}, :table "events_arc", :name "id"})
(def events_arc-ext_id {:type {:size 50, :name "VARCHAR"}, :table "events_arc", :name "ext_id"})
(def events_arc-type {:type {:size 128, :name "VARCHAR"}, :table "events_arc", :name "type"})
(def events_arc-date {:type {:size 19, :name "TIMESTAMP"}, :table "events_arc", :name "date"})
(def events_arc-value {:type {:size 8192, :name "VARCHAR"}, :table "events_arc", :name "value"})
(def events_arc-state {:type {:size 10, :name "INT"}, :table "events_arc", :name "state"})
(def events_arc-err_count {:type {:size 10, :name "INT"}, :table "events_arc", :name "err_count"})
(def events_arc-locked {:type {:size 1, :name "BIT"}, :table "events_arc", :name "locked"})

;;;; states
(def table-states "states")
(def states-id {:type {:size 10, :name "INT UNSIGNED"}, :table "states", :name "id"})
(def states-name {:type {:size 64, :name "VARCHAR"}, :table "states", :name "name"})

