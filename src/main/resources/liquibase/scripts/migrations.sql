--liquibase formatted sql

--changeset avvasil:1

CREATE TABLE animals
(
    id          serial          NOT NULL PRIMARY KEY,
    animal_name varchar(255)    NOT NULL,
    animal_age  integer
);

CREATE TABLE users
(
    id              serial          NOT NULL PRIMARY KEY,
    user_firstname  varchar(255)    NOT NULL,
    user_lastname   varchar(255)    NOT NULL
);