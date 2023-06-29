# LockedOut


### Use case

LockedOut is a authorization server that utilises the OAuth2.0 and OIDC protocol. LockedOut can be used in applications to support authentication and authorization. A user interface, can also be used to CRUD users/OAuth2.0 clients apps.

### Running

Ensure ports: 3000, 5432, 8080, 8081 are available.

Start the application using Docker Compose. Ensure you are inside the `docker-compose` folder.

```
docker compose -f docker-compose.dev.yml up -d
```

### Architecture overview

```
                                                   3
                           2    |--------------| -----> |-------|
                        |-----> | Identity API |        | PG DB |
                        |       |--------------| <----- |-------|
                        |               |          4
|----------|   1     |----------|       |
| Frontend | ------> | Auth API | <-----|
|----------|         |----------|    5
```

### Components

- The `Frontend` web app can be used to CRUD users/OAuth clients.
- The `Auth service API` is responsible for authenticating users and OAuth2.0 clients. It has a heavy dependency on the `Identity service API`.
- The `Identity service API` perform most of the CRUD operations and directly interacts with the Postgres database.