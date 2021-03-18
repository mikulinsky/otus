create table User
(
    id         bigint not null primary key,
    name       varchar(50),
    password   varchar(50),
    role       int
);