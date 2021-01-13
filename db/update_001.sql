create table if not exists role
(
    id   serial primary key,
    name varchar(255) not null unique
);

create table if not exists room
(
    id   serial primary key,
    name varchar(255) not null unique
);

create table if not exists person
(
    id       serial primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    role_id  int references role (id)
);

create table if not exists message
(
    id        serial primary key,
    text      varchar(1000) not null,
    created   timestamp,
    person_id int references person (id),
    room_id   int references room (id)
)