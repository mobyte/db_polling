(ns aggregation.db.utils
  (:use [libs.db.query]
	[db.conf]
	[db.entities]))

(defn get-event-by-name
  "Получение события по системному имени."
  [name]
  (first (with-db db (select table-events :where [:= events-name name]))))

(defn get-system-config-by-id
  "Получение настроек системы"
  [config-id]
  (first (with-db db (select table-system_config :where [:= system_config-id config-id]))))