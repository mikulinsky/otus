create table client
(
    id         bigint not null primary key,
    name       varchar(50),
    address_id bigint
);

create table address
(
    id     bigint not null primary key,
    street varchar(50) not null
);

create table phone
(
    id        bigint not null primary key,
    number    varchar(50) not null,
    client_id bigint not null
);