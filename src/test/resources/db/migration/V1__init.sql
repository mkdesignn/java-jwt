create table users(
    id bigint auto_increment,
    name varchar(255),
    username varchar(255) UNIQUE,
    password varchar(255),
    email varchar(255)
);