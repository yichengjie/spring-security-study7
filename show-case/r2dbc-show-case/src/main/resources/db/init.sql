CREATE TABLE person(
   id VARCHAR(255) PRIMARY KEY,
   name VARCHAR(255),
   age INT
);


create table if not exists user_react(
    id          char(64)    not null primary key,
    name        varchar(10) not null,
    account     varchar(15) not null,
    password    varchar(65) not null,
    role        char(5)     not null,
    insert_time datetime    not null default current_timestamp,
    update_time datetime    not null default current_timestamp on update current_timestamp,
    unique (account),
    index (role)
);