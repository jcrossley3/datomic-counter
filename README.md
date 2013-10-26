# Datomic Counter

Deploy this app to the nodes in your Immutant cluster after overlaying
the Hotrod modules on them.

## Usage

    $ lein immutant install LATEST
    $ lein immutant overlay hotrod

To easily simulate a cluster locally on your laptop:

    $ cp -R ~/.immutant/current/ /tmp/node1
    $ cp -R ~/.immutant/current/ /tmp/node2
    $ IMMUTANT_HOME=/tmp/node1 lein immutant run --clustered -Djboss.node.name=node1
    $ IMMUTANT_HOME=/tmp/node2 lein immutant run --clustered -Djboss.socket.binding.port-offset=100

Now start up your Datomic transactor:

    $ cd $DATOMIC_HOME
    $ bin/transactor inf-transactor.properties 
    
Then deploy the app to both nodes:

    $ cd <<where you cloned this repo>>
    $ IMMUTANT_HOME=/tmp/node1 lein immutant deploy
    $ IMMUTANT_HOME=/tmp/node2 lein immutant deploy

## License

Copyright © 2013 Jim Crossley

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
