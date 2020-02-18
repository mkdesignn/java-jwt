create table users(
    id int NOT NULL PRIMARY KEY auto_increment,
    name varchar(255),
    username varchar(255) UNIQUE,
    password varchar(255),
    email varchar(255)
);