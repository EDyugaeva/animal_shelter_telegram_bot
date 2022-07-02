--liquibase formatted sql

--changeset avvasil:1


CREATE TABLE pet_owner
(
    id                  serial          NOT NULL PRIMARY KEY,
    first_name          varchar(255)    NOT NULL,
    last_name           varchar(255)    NOT NULL,
    chat_id             bigint          NOT NULL,
    phone_number        bigint          NOT NULL,
    day_of_probation    integer         NOT NULL
);

CREATE TABLE report
(
    id                  serial          NOT NULL PRIMARY KEY,
    date_of_report      date            NOT NULL,
    diet                text            NOT NULL,
    health              text            NOT NULL,
    change_in_behavior  text            NOT NULL,
    is_report_checked   boolean         NOT NULL
);

CREATE TABLE volunteer
(
    id                  serial          NOT NULL PRIMARY KEY,
    first_name          varchar(255)    NOT NULL,
    last_name           varchar(255)    NOT NULL,
    extra_info          text            NOT NULL
);


CREATE TABLE pet
(
    id                  serial          NOT NULL PRIMARY KEY,
    name_of_pet         varchar(255)    NOT NULL,
    health              text            NOT NULL,
    extra_info_of_pet   text            NOT NULL
);


CREATE TABLE photo_of_pet
(
    id                  serial          NOT NULL PRIMARY KEY,
    file_path           text            NOT NULL,
    file_size           integer         NOT NULL,
    media_type          text            NOT NULL
);