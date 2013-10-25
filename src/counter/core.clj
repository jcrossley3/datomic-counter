(ns counter.core
  (:require [counter.db :as db]
            [immutant.jobs :as job]))

(defn work
  "Fires on schedule to dump/increment counter"
  []
  (println "counter value =" (db/value))
  (db/increment))

(defn start
  "Initialize the database and schedule the counter"
  []
  (try
    (db/init)
    (job/schedule "counter" work, :every [2 :seconds], :singleton false)
    (catch Exception e
      (println "Check the transactor, retrying in 10 seconds")
      (future (Thread/sleep 10000) (start)))))

(defn stop
  "Stop the counter"
  []
  (job/unschedule "counter"))
