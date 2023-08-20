-- app
create table app (
    id bigserial primary key,
    name varchar(50) not null,
    created_at timestamp not null
);

-- user
create table users (
    id bigserial primary key,
    username varchar(50) not null,
    password varchar(50) not null,
    email varchar(50) not null,
    phone_number varchar(50) null,
    fk_app_id int not null references app (id),
    created_at timestamp not null
);
-- user_role many-to-many
create table user_role (
    user_id int not null,
    role_id int not null
);
-- user_authority many-to-many
create table user_authority (
    user_id int not null,
    authority_id int not null
);

-- client
create table client (
    id bigserial primary key,
    client_id varchar(50) not null,
    secret varchar(50) not null,
    fk_app_id int not null references app (id),
    redirect_uri varchar(225) not null,
    created_at timestamp not null
);
-- client_role many-to-many
create table client_role (
    client_id int not null,
    role_id int not null
);
-- client_authority many-to-many
create table client_authority (
    client_id int not null,
    authority_id int not null
);
-- client_scope
create table client_scope (
    scope varchar(50) not null,
    fk_client_id int not null references client (id)
);
-- client_auth_method
create table client_auth_method (
    auth_method varchar(50) not null,
    fk_client_id int not null references client (id)
);
-- client_auth_grant_type
create table client_auth_grant_type (
    auth_grant_type varchar(50) not null,
    fk_client_id int not null references client (id)
);

-- authority
create table authority (
    id bigserial primary key,
    name varchar(50) not null
);

-- role
create table role (
    id bigserial primary key,
    name varchar(50) not null
);
