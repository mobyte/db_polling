(ns libs.dbpoll.glib)

(defn stacktrace-to-string [e]
  (let [sw (java.io.StringWriter.)
        pw (java.io.PrintWriter. sw)]
    (.printStackTrace e pw) (.toString sw)))

