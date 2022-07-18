--liquibase formatted sql

--changeSet avvasil:1

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

--changeSet edygaeva:1

ALTER TABLE pet_owner ADD CONSTRAINT chat_id_constraint UNIQUE (chat_id);

ALTER TABLE pet_owner ALTER COLUMN phone_number TYPE text;

ALTER TABLE pet_owner ALTER COLUMN  first_name DROP  NOT NULL;

ALTER TABLE pet_owner ALTER COLUMN  last_name DROP  NOT NULL;

ALTER TABLE pet_owner ALTER COLUMN  day_of_probation DROP  NOT NULL;

--changeSet edygaeva:2


ALTER TABLE photo_of_pet
    ADD COLUMN data BYTEA,
    ADD COLUMN report_id integer,
    ADD CONSTRAINT photo_of_pet_report_connection FOREIGN KEY (report_id) REFERENCES report(id);

ALTER TABLE pet
    ADD COLUMN pet_owner_id integer,
    ADD FOREIGN KEY (pet_owner_id) REFERENCES pet_owner(id);

ALTER TABLE report
    ADD COLUMN result text,
    ADD COLUMN pet_id integer,
    ADD FOREIGN KEY (pet_id) REFERENCES pet(id),
    ADD COLUMN pet_owner_id integer,
    ADD FOREIGN KEY (pet_owner_id) REFERENCES pet_owner(id);


--changeSet edugaeva:3

ALTER TABLE report ALTER COLUMN date_of_report TYPE TEXT;

ALTER TABLE report ALTER COLUMN  change_in_behavior DROP  NOT NULL;

ALTER TABLE report ALTER COLUMN  is_report_checked set default false;

ALTER TABLE report ALTER COLUMN  diet DROP  NOT NULL;

ALTER TABLE photo_of_pet ALTER COLUMN file_path DROP NOT NULL;

ALTER TABLE photo_of_pet ALTER COLUMN file_size DROP NOT NULL;

ALTER TABLE photo_of_pet ALTER COLUMN media_type DROP NOT NULL;


--changeSet avvasil:2

ALTER TABLE volunteer ADD COLUMN chat_id bigint;

--changeSet avvasil:3

ALTER TABLE volunteer ADD COLUMN phone_number text;

--changeSet edugaeva:4

ALTER TABLE pet_owner ALTER COLUMN phone_number DROP NOT NULL;

--changeSet edygaeva:4

ALTER TABLE photo_of_pet add COLUMN url text;


--changeSet edygaeva:6

ALTER TABLE volunteer ALTER COLUMN extra_info DROP NOT NULL;


--changeSet edygaeva:7

ALTER TABLE photo_of_pet drop COLUMN media_type;


--changeSet edygaeva:8

ALTER TABLE photo_of_pet drop COLUMN url;

