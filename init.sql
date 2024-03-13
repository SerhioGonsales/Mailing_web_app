create database if not exists email_db;

use email_db;

create table if not exists emails
(
    is_email_sent       bit null,
    id                  bigint auto_increment primary key,
    email               varchar(255) not null, unique (email)
);

create database if not exists message_db;

use message_db;

create table if not exists messages
(
    id                  bigint auto_increment primary key,
    file_name           varchar(255) null,
    file_path           varchar(255) null,
    subject             varchar(255) null,
    text                mediumtext null
);

ALTER TABLE messages MODIFY COLUMN text MEDIUMTEXT NOT NULL;

ALTER TABLE messages CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create database if not exists content_db;

use content_db;

create table if not exists attached_files
(
id                  bigint auto_increment primary key,
file_path           varchar(255) null,
file_name           varchar(255) null
);

ALTER TABLE attached_files CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create table if not exists subjects
(
    id                  bigint auto_increment primary key,
    subject             varchar(255) not null
);

ALTER TABLE subjects CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create table if not exists texts
(
    id                  bigint auto_increment primary key,
    content             mediumtext null,
    file_name           varchar(255) null
);

ALTER TABLE texts MODIFY COLUMN content MEDIUMTEXT NOT NULL;

ALTER TABLE texts CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;



# create table if not exists mailing_db.texts
# (
#     id                 bigint auto_increment primary key,
#     content            mediumtext not null,
#     file_name          varchar(255) null
# );
#
# create table if not exists mailing_db.attached_files
# (
#     id                 bigint auto_increment primary key,
#     file_path          varchar(255) null,
#     file_name          varchar(255) null
# );
#
# create table if not exists mailing_db.messages
# (
#     id                 bigint auto_increment primary key,
#     subject_id         bigint       null,
#     text_id            bigint       null,
#     attached_file_id   bigint       null,
#     foreign key (subject_id) references mailing_db.subjects(id),
#     foreign key (text_id) references mailing_db.texts (id),
#     foreign key (attached_file_id) references mailing_db.attached_files (id)
# );
#
# create table if not exists mailing_db.emails
# (
#     id                 bigint auto_increment primary key,
#     email              varchar(255) not null,
#     sent               bit,
#     message_id         bigint       null,
#     foreign key (message_id) references mailing_db.messages (id)
# );
