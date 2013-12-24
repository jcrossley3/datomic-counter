(defproject counter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.infinispan/infinispan-client-hotrod "6.0.0.Final"]
                 [com.datomic/datomic-pro "0.9.4314"]]
  :immutant {:init counter.core/start})
