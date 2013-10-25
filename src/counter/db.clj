(ns counter.db
  (:require [immutant.util :as util]
            [datomic.api :as d]
            [clojure.java.io :as io]))

(defn init
  "Create the database, load the schema, and create a counter"
  []
  (let [uri (format "datomic:inf://localhost:%d/%s"
                    (util/port :hotrod)
                    (util/app-name))]
    (if (d/create-database uri)
      (let [c (d/connect uri)]
        @(d/transact c (read-string (slurp (io/resource "schema.dtm"))))
        @(d/transact c [{:db/id #db/id[:db.part/user]
                         :counter/id :incrementer
                         :counter/value 0}])))))
