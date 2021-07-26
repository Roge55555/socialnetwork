# drop database if exists socialnetworkdb;
# drop user if exists 'user';
# /*drop user if exists 'admin';*/
#
# create database socialnetworkdb;
# create user 'user' identified by 'user';
# /*create user 'admin' identified by 'admin';*/
#
# grant all on socialnetworkdb.* to 'user';
# /*grant all on socialnetworkdb.* to 'admin';*/
#
# /*set password for springuser = 'password';*/
#
#  SET GLOBAL time_zone = '+3:00';


# USE socialnetworkdb;

CREATE TABLE access_role
(
    id        bigint PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL unique
);
insert into access_role
values (1, 'ADMIN');
insert into access_role
values (2, 'USER');


CREATE TABLE `user`
(
    id                bigint PRIMARY KEY AUTO_INCREMENT,
    login             VARCHAR(255) NOT NULL unique,
    `password`        VARCHAR(255) NOT NULL,
    date_birth        date,
    first_name        VARCHAR(50)  NOT NULL,
    last_name         VARCHAR(50)  NOT NULL,
    email             VARCHAR(255) NOT NULL UNIQUE,
    phone             VARCHAR(20)  NOT NULL UNIQUE,
    `role`            bigint       NOT NULL,
    is_active         BOOL DEFAULT false,
    is_blocked        BOOL DEFAULT false,
    registration_date date         NOT NULL,
    website           VARCHAR(255) NULL,
    about_yourself    VARCHAR(255) NULL,
    job_title         VARCHAR(255) NULL,
    work_phone        VARCHAR(20)  NULL,
    foreign key (`role`) references access_role (id)
);
insert into `user`
values (1, 'rogE', '$2y$12$.ZAsJHbg3ZUcGe/oPF/STey9e2UGDzjQ3rFpCc7jt1TAShufFjBHC', '1998-05-26', 'Egor', 'Perehodko',
        'roge55555@gmail.com', '+375293039973', 1, false, false, '2021-06-24', null,
        'nothing special', 'sysadmin', '+375293039973');
insert into `user`
values (2, 'CtrogE', '$2y$12$cWWU.g2PIX8rrqxb4Jybr.N5.57dnwbiCU5CFIbQspce6yT/25brC', '1998-05-13', 'Roge', 'Ctrannik',
        'ctrannik555@gmail.com', '+375333236700', 1, false, false, '2021-06-24', 'vk.com',
        null, null, '+375333236700');
insert into `user`
values (3, 'Roma666', '$2y$12$nA5jedTlkTE03eovTVVWxeQ0YLB1195XI6Hg/dlcu0bsth/mAHA5C', '1999-03-18', 'Romann', 'Ranshov',
        'sportzman@gmail.com', '+375293486999', 2, false, false, '2021-06-28', 'linkedin.com/rmn18',
        'msmc', 'IP', '+375335485698');

CREATE TABLE profile_comment
(
    id               bigint PRIMARY KEY AUTO_INCREMENT,
    profile_owner_id bigint       NOT NULL,
    user_id          bigint       NOT NULL,
    `date`           datetime     NOT NULL,
    comment_txt      VARCHAR(255) NOT NULL,
    foreign key (profile_owner_id) references `user` (id),
    foreign key (user_id) references `user` (id)
);
insert into profile_comment
values (1, 1, 2, '2021-07-05 11:23:50', 'first comment');
insert into profile_comment
values (2, 2, 3, '2021-07-05 20:34:47', 'second comment');

CREATE TABLE user_event
(
    id            bigint PRIMARY KEY AUTO_INCREMENT,
    user_id       bigint,
    `name`        VARCHAR(70)  NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `date`        datetime     NOT NULL,
    foreign key (user_id) references `user` (id)
);
insert into user_event
values (1, 3, 'birthday', 'admin`s birthday', '2022-05-26');

CREATE TABLE role_list
(
    id     bigint PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE
);
insert into role_list
values (1, 'Friend');
insert into role_list
values (2, 'Best Friend');
insert into role_list
values (3, 'Workmate');
insert into role_list
values (4, 'Director');
insert into role_list
values (5, 'Me ^_^');

CREATE TABLE contact
(
    id             bigint PRIMARY KEY AUTO_INCREMENT,
    creator_id     bigint,
    mate_id        bigint,
    date_connected date NOT NULL,
    contact_level  boolean,
    contact_role   bigint,
    foreign key (creator_id) references `user` (id),
    foreign key (mate_id) references `user` (id),
    foreign key (contact_role) references role_list (id)
);
insert into contact
values (1, 2, 1, '2021-06-25', true, 5);
insert into contact
values (2, 3, 1, '2021-07-17', false, 3);

CREATE TABLE message
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    sender_id    bigint,
    receiver_id  bigint,
    date_created datetime     NOT NULL,
    message_txt  VARCHAR(255) NOT NULL,
    foreign key (sender_id) references `user` (id),
    foreign key (receiver_id) references `user` (id)
);
insert into message
values (1, 1, 2, '2021-06-25 17:45:31', 'hai');
insert into message
values (2, 2, 1, '2021-06-25 17:47:27', 'hello');
insert into message
values (3, 2, 1, '2021-06-25 17:47:51', 'how are you?');
insert into message
values (4, 1, 2, '2021-06-25 17:48:27', 'shaking like hell on interview...');
insert into message
values (5, 2, 1, '2021-06-25 17:47:33', '=/');

CREATE TABLE community
(
    id            bigint PRIMARY KEY AUTO_INCREMENT,
    creator_id    bigint,
    `name`        VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    date_created  date        NOT NULL,
    foreign key (creator_id) references `user` (id)
);
insert into community
values (1, 1, 'admin`s home', 'description.....', '2021-06-25');
insert into community
values (2, 2, 'free community', 'description.....', '2021-07-10');

CREATE TABLE user_of_community
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    community_id bigint,
    user_id      bigint,
    date_entered date NOT NULL,
    foreign key (community_id) references community (id),
    foreign key (user_id) references `user` (id)
);
insert into user_of_community
values (1, 1, 1, '2021-06-25');
insert into user_of_community
values (2, 1, 2, '2021-06-25');

CREATE TABLE community_message
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    creator      bigint,
    community_id bigint,
    `date`       datetime     NOT NULL,
    txt          VARCHAR(255) NOT NULL,
    foreign key (creator) references `user` (id),
    foreign key (community_id) references community (id)
);
insert into community_message
values (1, 1, 1, '2021-06-25 17:58:31', 'don`t know what to write...');
insert into community_message
values (2, 2, 1, '2021-06-25 17:59:27', 'same, dude');
insert into community_message
values (3, 2, 1, '2021-06-25 17:59:51', 'talk with yourself leads to mental problems');

CREATE TABLE blocklist
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    community_id bigint,
    who_baned    bigint,
    whom_baned   bigint,
    block_date   date         NOT NULL,
    block_cause  VARCHAR(255) NOT NULL,
    foreign key (community_id) references community (id),
    foreign key (who_baned) references `user` (id),
    foreign key (whom_baned) references `user` (id)
);
insert into blocklist
values (1, 1, 3, 1, '2021-07-01', 'text Y.A.N.G.I!!!!!');
