create table Client
(
    id   bigint not null primary key,
    name varchar(50),
    age  int
);
create table Account
(
    id   bigint not null primary key,
    no   varchar(50),
    type varchar(50),
    rest real
);