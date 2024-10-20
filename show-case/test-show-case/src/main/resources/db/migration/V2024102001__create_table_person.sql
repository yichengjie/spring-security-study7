CREATE TABLE person (
  id varchar(255) NOT NULL PRIMARY KEY,
  name varchar(255) DEFAULT NULL,
  age int(11) DEFAULT NULL
) ;


insert into person (id, name, age) values ('1', 'Alice', 20);
insert into person (id, name, age) values ('2', 'Bob', 30);
insert into person (id, name, age) values ('3', 'Carol', 40);
insert into person (id, name, age) values ('4', 'Dave', 50);