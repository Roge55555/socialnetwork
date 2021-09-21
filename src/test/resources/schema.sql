CREATE TABLE IF NOT EXISTS access_role
(
    id        bigint PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL unique
);

CREATE TABLE IF NOT EXISTS `user`
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
    registration_date date         NOT NULL,
    website           VARCHAR(255) NULL,
    about_yourself    VARCHAR(255) NULL,
    job_title         VARCHAR(255) NULL,
    work_phone        VARCHAR(20)  NULL,
    foreign key (`role`) references access_role (id)
);

CREATE TABLE IF NOT EXISTS profile_comment
(
    id               bigint PRIMARY KEY AUTO_INCREMENT,
    profile_owner_id bigint       NOT NULL,
    user_id          bigint       NOT NULL,
    `date`           datetime     NOT NULL,
    comment_txt      VARCHAR(255) NOT NULL,
    foreign key (profile_owner_id) references `user` (id),
    foreign key (user_id) references `user` (id)
);

CREATE TABLE IF NOT EXISTS user_event
(
    id            bigint PRIMARY KEY AUTO_INCREMENT,
    user_id       bigint,
    `name`        VARCHAR(70)  NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `date`        datetime     NOT NULL,
    foreign key (user_id) references `user` (id)
);

CREATE TABLE IF NOT EXISTS role_list
(
    id     bigint PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS contact
(
    id             bigint PRIMARY KEY AUTO_INCREMENT,
    creator_id     bigint,
    mate_id        bigint,
    date_connected date NOT NULL,
    contact_level  boolean,
    creator_role   bigint,
    mate_role      bigint,
    foreign key (creator_id) references `user` (id),
    foreign key (mate_id) references `user` (id),
    foreign key (creator_role) references role_list (id),
    foreign key (mate_role) references role_list (id),
    UNIQUE (creator_id, mate_id)
);

CREATE TABLE IF NOT EXISTS message
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    sender_id    bigint,
    receiver_id  bigint,
    date_created datetime     NOT NULL,
    message_txt  VARCHAR(255) NOT NULL,
    foreign key (sender_id) references `user` (id),
    foreign key (receiver_id) references `user` (id)
);

CREATE TABLE IF NOT EXISTS community
(
    id            bigint PRIMARY KEY AUTO_INCREMENT,
    creator_id    bigint,
    `name`        VARCHAR(50) NOT NULL,
    `description` VARCHAR(255),
    date_created  date        NOT NULL,
    foreign key (creator_id) references `user` (id)
);

CREATE TABLE IF NOT EXISTS user_of_community
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    community_id bigint,
    user_id      bigint,
    date_entered date NOT NULL,
    foreign key (community_id) references community (id),
    foreign key (user_id) references `user` (id),
    UNIQUE (community_id, user_id)
);

CREATE TABLE IF NOT EXISTS community_message
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    creator      bigint,
    community_id bigint,
    `date`       datetime     NOT NULL,
    txt          VARCHAR(255) NOT NULL,
    foreign key (creator) references `user` (id),
    foreign key (community_id) references community (id)
);

CREATE TABLE IF NOT EXISTS blocklist
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    community_id bigint,
    who_baned    bigint,
    whom_baned   bigint,
    block_date   date         NOT NULL,
    block_cause  VARCHAR(255) NOT NULL,
    foreign key (community_id) references community (id),
    foreign key (who_baned) references `user` (id),
    foreign key (whom_baned) references `user` (id),
    UNIQUE (community_id, who_baned, whom_baned)
);
