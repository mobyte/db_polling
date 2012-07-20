(ns db.conf)

(def db-variant {:direct {:classname "com.mysql.jdbc.Driver"
                          :subprotocol "mysql"
                          :subname (str "//localhost:3306/aggregation_system")
                          :user "aggregsys"
                          :password "aggregsys"}
                 :connpool {:name "aggregsys_db"}})

(def db (db-variant :direct))
