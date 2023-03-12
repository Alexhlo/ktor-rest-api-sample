# Ktor sample RESTful API

Provides basic methods to work with Customer service.

### Using
- Swagger
- PostgreSQL
- Junit-jupiter
- Prometheus

### HttpMethods
##### Students controller (/postgres):
- GET /customers/get/{id} - **get customer by id**;
- GET /customers/get/all - **get all customers**;

- POST /customers/save - **save customer as request body**
- POST /customers/save/all - **save all customers in request body**
- POST /customers/update - **update customer by request body**

- DELETE /customers/delete/{id} - **delete customer by id**
---

## Run application

- ### As single application
##### Start PostgreSQL as docker container:
`
docker run
--name postgres_test
-e POSTGRES_USER=testuser
-e POSTGRES_PASSWORD=123
-e POSTGRES_DB=shop
-p 5432:5432
-d
postgres
`
##### Setup ktor-application config: **application.conf** (HOCON style)
```HOCON
ktor {
    deployment {
        port = 8888
    }
    application {
        modules = [ com.example.ApplicationKt.module ]
    }
}

db {
    config {
       driver = "org.postgresql.Driver"
       jdbcURL = "jdbc:postgresql://localhost:5432/shop"
       username: "testuser"
       password: "123"
    }
}

swagger {
        host = "localhost:8888"
    }
```