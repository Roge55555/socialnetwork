create database socialnetworkdb;
create user 'user' identified by 'user';
create user 'admin' identified by 'admin';
grant all on socialnetworkdb.* to 'user';
grant all on socialnetworkdb.* to 'admin';

set password for springuser = 'password';

SET GLOBAL time_zone = '+3:00';


USE socialnetworkdb;
 
CREATE TABLE access_role
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL unique
);

CREATE TABLE `user`
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL unique,
    `password` VARCHAR(255) NOT NULL,
    date_birth date,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE,
    `role` bigint NOT NULL,
    is_active BOOL DEFAULT false,
    is_blocked BOOL DEFAULT false,
    registration_date date NOT NULL,
    website VARCHAR(255) NULL,
    about_yourself VARCHAR(255) NULL,
    jobtitle VARCHAR(255) NULL,
    workphone VARCHAR(20) NULL,
    foreign key (`role`) references access_role (id)
);

CREATE TABLE profile_comment
(
	profile_owner_id bigint,
    user_id bigint,
    comment_date datetime NOT NULL,
    comment_txt VARCHAR(255) NOT NULL,
    foreign key (profile_owner_id) references `user` (id),
    foreign key (user_id) references `user` (id)
);

CREATE TABLE user_event
(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    user_id bigint,
    `name` VARCHAR(70) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `date` datetime NOT NULL,
    foreign key (user_id) references `user` (id)
);

CREATE TABLE role_list
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE contact
(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	creator_id bigint,
    mate_id bigint,
    date_connected date NOT NULL,
    contact_level boolean,
    contact_role bigint,
    foreign key (creator_id) references `user` (id),
    foreign key (mate_id) references `user` (id),
    foreign key (contact_role) references role_list (id)
);

CREATE TABLE message
(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    sender_id bigint,
    receiver_id bigint,
    date_created datetime NOT NULL,
    message_txt VARCHAR(255) NOT NULL,
    foreign key (sender_id) references `user` (id),
    foreign key (receiver_id) references `user` (id)
);

CREATE TABLE community
(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    creator_id bigint,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    date_created date NOT NULL,
    foreign key (creator_id) references `user` (id)
);

CREATE TABLE user_of_community
(
	community_id bigint,
    user_id bigint,
    date_entered date NOT NULL,
    foreign key (community_id) references communite (id),
    foreign key (user_id) references `user` (id)
);

CREATE TABLE community_message
(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    creator bigint,
    community_id bigint,
    `date` datetime NOT NULL,
    txt VARCHAR(255) NOT NULL,
    foreign key (creator) references `user` (id),
    foreign key (community_id) references communite (id)
);

CREATE TABLE blocklist
(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    community_id bigint,
    who_baned bigint,
    whom_baned bigint,
    block_date date NOT NULL,
    block_cause VARCHAR(255) NOT NULL,
    foreign key (community_id) references communite (id),
    foreign key (who_baned) references `user` (id),
    foreign key (whom_baned) references `user` (id)
);
