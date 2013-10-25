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
  (db/init)
  (job/schedule "counter" work, :every :second, :singleton false))

(defn stop
  "Stop the counter"
  []
  (job/unschedule "counter"))
