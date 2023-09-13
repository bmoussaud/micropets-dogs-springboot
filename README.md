# Dog MicroService

This is a sample of a Java Spring app that works with Tilt and the Tanzu Application Platform.
It provides the framework to enhance the MicroPet Application

## Dependencies
1. [kubectl CLI](https://kubernetes.io/docs/tasks/tools/)
1. [Tilt version >= v0.23.2](https://docs.tilt.dev/install.html)
1. Tanzu CLI and the apps plugin v0.2.0 which are provided as part of [Tanzu Application Platform](https://network.tanzu.vmware.com/products/tanzu-application-platform)
1. A cluster with Tanzu Application Platform, and the ["Micropets Supply Chain"](https://github.com/bmoussaud/micropets-app-operator), plus its dependencies. This supply chains concept is part of [Tanzu Application Platform](https://network.tanzu.vmware.com/products/tanzu-application-platform).

## Deploy the accelerarator

```
tanzu acc create micropets-java-service-accelerator --git-repo https://github.com/bmoussaud/micropet-java-service-accelerator --git-branch main --interval 5s
```

## Running the sample


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


### With the Database

```
kubectl exec -ti pod/dogs-database-0 -- pg_autoctl show state
```
```
kubectl exec -it dogs-database-0 -- bash -c "psql"
postgres-# \l
                                                          List of databases
      Name       |           Owner           | Encoding | Collate |  Ctype  |                    Access privileges
-----------------+---------------------------+----------+---------+---------+---------------------------------------------------------
 postgres        | postgres                  | UTF8     | C.UTF-8 | C.UTF-8 | postgres=CTc/postgres                                  +
                 |                           |          |         |         | pgautofailover_monitor=c/postgres                      +
                 |                           |          |         |         | postgres_exporter=c/postgres
 dogs-database | pgautofailover_replicator | UTF8     | C.UTF-8 | C.UTF-8 | pgautofailover_replicator=CTc/pgautofailover_replicator+
                 |                           |          |         |         | admin=CTc/pgautofailover_replicator
 template0       | postgres                  | UTF8     | C.UTF-8 | C.UTF-8 | =c/postgres                                            +
                 |                           |          |         |         | postgres=CTc/postgres
 template1       | postgres                  | UTF8     | C.UTF-8 | C.UTF-8 | =c/postgres                                            +
                 |                           |          |         |         | postgres=CTc/postgres
(4 rows)
postgres-# \c dogs-database
You are now connected to database "dogs-database" as user "postgres".
dogs-database=#
dogs-database=# \dt
       List of relations
 Schema | Name  | Type  | Owner
--------+-------+-------+-------
 public | Dog | table | admin
(1 row)
dogs-database=# select * from Dog;
```


## Observability

* https://www.appsdeveloperblog.com/micrometer-and-zipkin-in-spring-boot
