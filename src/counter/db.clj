(ns counter.db
  (:require [immutant.util :as util]
            [datomic.api :as d]
            [clojure.java.io :as io]))

(def uri (format "datomic:inf://localhost:%d/%s"
                 (util/port :hotrod)
                 (util/app-name)))

(def conn (delay (d/connect uri)))

(defn init
  "Create the database, load the schema, initialize counter, and
  define transaction function named :increment"
  []
  (when (d/create-database uri)
    @(d/transact @conn (read-string (slurp (io/resource "schema.dtm"))))
    @(d/transact @conn [{:db/id :counter :value 0}
                        {:db/id (d/tempid :db.part/user)
                         :db/ident :increment
                         :db/fn (d/function
                                 {:lang "clojure"
                                  :params '[db]
                                  :code '(let [v (:value (d/entity db :counter))]
                                           (println "inc" v)
                                           [{:db/id :counter
                                             :value (inc v)}])})}])))

(defn value
  "The current value of the counter"
  []
  (:value (d/entity (d/db @conn) :counter)))

(defn increment
  "Invoke a transaction function to increment the counter"
  []
  @(d/transact @conn [[:increment]]))
