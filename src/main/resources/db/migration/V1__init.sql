create table users(
    id bigint primary key unique auto_increment,
    name varchar(255),
    username varchar(255),
    password varchar(255),
    email varchar(255)
);