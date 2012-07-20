(defproject crystal.clojure_libs/db_polling "0.1"
  :description "Crystal Spring DB Polling clojure library"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [crystal.clojure_libs/query_libs "0.2"]
                 [com.mysql/jdbc "5.1.5"]]
  :dev-dependencies [[org.clojure/clojure "1.4.0"]
                     [swank-clojure "1.3.3"]
                     [crystal.clojure_libs/query_libs "0.2"]
                     [com.mysql/jdbc "5.1.5"]]
  :compile-path  "build"
  :library-path  "libs"
  :resources-path "war/resources"
  :aot [libs.dbpoll.manager libs.dbpoll.error])
