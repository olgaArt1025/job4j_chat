create table if not exists person (
    id serial primary key not null,
    login varchar(2000),
    password varchar(2000)
    );

create table if not exists role (
       id serial primary key not null,
       name varchar(100)
    );

CREATE TABLE if not exists person_role (
    role_id integer NOT NULL,
    person_id integer  NOT NULL,
    PRIMARY KEY (role_id , person_id ),
    FOREIGN KEY (role_id) REFERENCES role,
    FOREIGN KEY (person_id ) REFERENCES person
    );


insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');

create table if not exists room (
      id serial primary key not null,
      name varchar(100)
    );

create table if not exists message (
     id serial primary key,
     message TEXT,
     room_id int not null references room(id),
     person_id int not null references person(id)
    );