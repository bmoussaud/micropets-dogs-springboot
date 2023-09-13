# Dog MicroService

This is a sample of a Java Spring app that works with Tilt and the Tanzu Application Platform.
It provides the framework to enhance the MicroPet Application with a new kind of service `Dog`

## Running the serice

Start the app deployment by running:

```
tilt up
```

You can hit the spacebar to open the UI in a browser. 

- > If you see an "Update error" message like the one below, then just follow the instructions and allow that context:
    ```
    Stop! tap-beta2 might be production.
    If you're sure you want to deploy there, add:
        allow_k8s_contexts('tap-beta2')
    to your Tiltfile. Otherwise, switch k8s contexts and restart Tilt.
    ```
## Usefull commands 

### With the Kubernetes

Configure a new database and the service bindings

````
kubectl apply -f config/app-operator
````

Clean up additional resources, including database
````
kubectl delete -f config/app-operator
kubectl delete workloads.carto.run dogs
````

### With Tanzu CLI 

```
➜ tanzu service class list                                                                                                                                              
  NAME                  DESCRIPTION
  kafka-unmanaged       Kafka by Bitnami
  mongodb-unmanaged     MongoDB by Bitnami
  mysql-unmanaged       MySQL by Bitnami
  postgresql-unmanaged  PostgreSQL by Bitnami
  rabbitmq-unmanaged    RabbitMQ by Bitnami
  redis-unmanaged       Redis by Bitnami
```

```
tanzu service class-claim create dogs-pgsql --class postgresql-unmanaged --parameter storageGB=1
```

```
❯ tanzu service class-claim list                                                                                                                                         
  NAME        CLASS                 READY  REASON
  dogs-pgsql  postgresql-unmanaged  True   Ready
```
### With the Database

```
❯ kubectl exec -ti pod/micropets-dev-database-0 -- pg_autoctl show state                                                                                                 
Defaulted container "pg-container" out of: pg-container, instance-logging, reconfigure-instance, postgres-metrics-exporter, postgres-sidecar
  Name |  Node |                                                                                  Host:Port |       TLI: LSN |   Connection |      Reported State |      Assigned State
-------+-------+--------------------------------------------------------------------------------------------+----------------+--------------+---------------------+--------------------
node_1 |     1 | micropets-dev-database-0.micropets-dev-database-agent.micropets-dev.svc.cluster.local:5432 |   1: 0/2FAF1F8 |   read-write |              single |              single

```
```
kubectl exec -ti pod/micropets-dev-database-0 -- bash -c "psql"
➜ kubectl exec -ti pod/micropets-dev-database-0 -- bash -c "psql"                                                                                                       
Defaulted container "pg-container" out of: pg-container, instance-logging, reconfigure-instance, postgres-metrics-exporter, postgres-sidecar
psql (15.1 (VMware Postgres 15.1.0))
Type "help" for help.

postgres=# \l
                                                                             List of databases
          Name          |           Owner           | Encoding | Collate |  Ctype  | ICU Locale | Locale Provider |                    Access privileges
------------------------+---------------------------+----------+---------+---------+------------+-----------------+---------------------------------------------------------
 micropets-dev-database | pgautofailover_replicator | UTF8     | C.UTF-8 | C.UTF-8 |            | libc            | pgautofailover_replicator=CTc/pgautofailover_replicator+
                        |                           |          |         |         |            |                 | postgres_exporter=c/pgautofailover_replicator          +
                        |                           |          |         |         |            |                 | admin=CTc/pgautofailover_replicator                    +
                        |                           |          |         |         |            |                 | pgrouser=c/pgautofailover_replicator                   +
                        |                           |          |         |         |            |                 | pgrwuser=c/pgautofailover_replicator
 postgres               | postgres                  | UTF8     | C.UTF-8 | C.UTF-8 |            | libc            | postgres=CTc/postgres                                  +
                        |                           |          |         |         |            |                 | pgautofailover_monitor=c/postgres                      +
                        |                           |          |         |         |            |                 | postgres_exporter=c/postgres
 template0              | postgres                  | UTF8     | C.UTF-8 | C.UTF-8 |            | libc            | =c/postgres                                            +
                        |                           |          |         |         |            |                 | postgres=CTc/postgres
 template1              | postgres                  | UTF8     | C.UTF-8 | C.UTF-8 |            | libc            | =c/postgres                                            +
                        |                           |          |         |         |            |                 | postgres=CTc/postgres
postgres=# \c micropets-dev-databas
connection to server on socket "/tmp/.s.PGSQL.5432" failed: FATAL:  database "micropets-dev-databas" does not exist
Previous connection kept
postgres=# \c micropets-dev-database
You are now connected to database "micropets-dev-database" as user "postgres".
micropets-dev-database=# \dt
             List of relations
 Schema |       Name       | Type  | Owner
--------+------------------+-------+-------
 public | bird             | table | admin
 public | dog_bean         | table | admin
 public | dog_bean_table   | table | admin
 public | dogs             | table | admin
 public | snake_bean       | table | admin
 public | snake_bean_table | table | admin
(6 rows)
```


## Observability

* https://www.appsdeveloperblog.com/micrometer-and-zipkin-in-spring-boot
