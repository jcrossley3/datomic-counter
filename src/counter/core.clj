(ns counter.core
  (:require [counter.db :as db]
            [immutant.jobs :as job]
            [clojure.tools.logging :as log]))

(defn job
  "Fires on schedule to dump/increment counter"
  []
  (let [before (db/value)]
    (db/increment)
    (println (format "counter: %d ==> %d" before (db/value)))))

(defn start
  "Initialize the database and schedule the counter"
  []
  (try
    (db/init)
    (job/schedule "counter" job, :every [2 :seconds], :singleton false)
    (catch Exception e
      (log/error e "Check transactor failings or version conflicts, retrying in 10 seconds")
      (future (Thread/sleep 10000) (start)))))

(defn stop
  "Stop the counter"
  []
  (job/unschedule "counter"))
