drop table user;

create table user (
    id serial primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null
);

insert into user (name, email, password) values('user', 'user@mail.ru', 'userpwd');
insert into user (name, email, password) values('admin', 'admin@mail.ru', 'adminpwd');

