-- request
create table audit_request (
    id bigserial primary key,
    correlation_id varchar(50) not null,
    process varchar(50) not null,
    request_type varchar(50) not null,
    request_status varchar(50) not null,
    created_at timestamp not null,
    log varchar(50) null
);