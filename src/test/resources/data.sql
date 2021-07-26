insert into access_role (id, role_name)
values (1, 'ADMIN'),
       (2, 'USER');

-- password: 55555
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`, is_active,
                    is_blocked, registration_date, website, about_yourself, job_title, work_phone)
values (1, 'rogE', '$2y$12$.ZAsJHbg3ZUcGe/oPF/STey9e2UGDzjQ3rFpCc7jt1TAShufFjBHC', '1998-05-26', 'Egor', 'Perehodko',
        'roge55555@gmail.com', '+375293039973', 1, false, false, '2021-06-24', null, 'nothing special', 'sysadmin', '+375293039973');

-- password: 131313
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`, is_active,
                    is_blocked, registration_date, website, about_yourself, job_title, work_phone)
values (2, 'CtrogE', '$2y$12$cWWU.g2PIX8rrqxb4Jybr.N5.57dnwbiCU5CFIbQspce6yT/25brC', '1998-05-13', 'Roge', 'Ctrannik',
        'ctrannik555@gmail.com', '+375333236700', 1, false, false, '2021-06-24', 'vk.com',null, null, '+375333236700');

-- password: 54862
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`, is_active,
                    is_blocked, registration_date, website, about_yourself, job_title, work_phone)
values (3, 'Roma666', '$2y$12$nA5jedTlkTE03eovTVVWxeQ0YLB1195XI6Hg/dlcu0bsth/mAHA5C', '1999-03-18', 'Romann', 'Ranshov',
        'sportzman@gmail.com', '+375293486999', 2, false, false, '2021-06-28', 'linkedin.com/rmn18', 'msmc', 'IP', '+375335485698');

insert into profile_comment (id, profile_owner_id,  user_id, `date`, comment_txt) values (1, 1, 2, '2021-07-05 11:23:50', 'first comment');
insert into profile_comment (id, profile_owner_id,  user_id, `date`, comment_txt) values (2, 2, 3, '2021-07-05 20:34:47', 'second comment');

insert into user_event (id, user_id,  `name`, `description`, `date`) values (1, 3, 'birthday', 'admin`s birthday', '2022-05-26');

insert into role_list (id, name) values (1, 'Friend');
insert into role_list (id, name) values (2, 'Best Friend');
insert into role_list (id, name) values (3, 'Workmate');
insert into role_list (id, name) values (4, 'Director');
insert into role_list (id, name) values (5, 'Me ^_^');

insert into contact (id, creator_id, mate_id, date_connected, contact_level, contact_role) values (1, 2, 1, '2021-06-25', true, 5);
insert into contact (id, creator_id, mate_id, date_connected, contact_level, contact_role) values (2, 3, 1, '2021-07-17', false, 3);

insert into message (id, sender_id, receiver_id, date_created, message_txt) values (1, 1, 2, '2021-06-25 17:45:31', 'hai');
insert into message (id, sender_id, receiver_id, date_created, message_txt) values (2, 2, 1, '2021-06-25 17:47:27', 'hello');
insert into message (id, sender_id, receiver_id, date_created, message_txt) values (3, 2, 1, '2021-06-25 17:47:51', 'how are you?');
insert into message (id, sender_id, receiver_id, date_created, message_txt) values (4, 1, 2, '2021-06-25 17:48:27', 'shaking like hell on interview...');
insert into message (id, sender_id, receiver_id, date_created, message_txt) values (5, 2, 1, '2021-06-25 17:47:33', '=/');

insert into community (id, creator_id, `name`, description, date_created) values (1, 1, 'admin`s home', 'description.....', '2021-06-25');
insert into community (id, creator_id, `name`, description, date_created) values (2, 2, 'free community', 'description.....', '2021-07-10');

insert into user_of_community (id, community_id, user_id, date_entered) values (1, 1, 1, '2021-06-25');
insert into user_of_community (id, community_id, user_id, date_entered) values (2, 1, 2, '2021-06-25');

insert into community_message (id, creator, community_id, `date`, txt) values (1, 1, 1, '2021-06-25 17:58:31', 'don`t know what to write...');
insert into community_message (id, creator, community_id, `date`, txt) values (2, 2, 1, '2021-06-25 17:59:27', 'same, dude');
insert into community_message (id, creator, community_id, `date`, txt) values (3, 2, 1, '2021-06-25 17:59:51', 'talk with yourself leads to mental problems');

insert into blocklist (id, community_id, who_baned, whom_baned, block_date, block_cause) values (1, 1, 3, 1, '2021-07-01', 'text Y.A.N.G.I!!!!!');
