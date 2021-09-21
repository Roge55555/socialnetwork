insert into access_role (id, role_name)
values (1, 'ADMIN'),
       (2, 'USER');

-- password: 55555
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`,
                    registration_date, website, about_yourself, job_title, work_phone)
values (1, 'rogE', '$2y$12$.ZAsJHbg3ZUcGe/oPF/STey9e2UGDzjQ3rFpCc7jt1TAShufFjBHC', '1998-05-26', 'Egor', 'Perehodko',
        'roge55555@gmail.com', '+375293039973', 1, '2021-06-24', null, 'nothing special', 'sysadmin',
        '+375293039973');

-- password: 131313
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`,
                    registration_date, website, about_yourself, job_title, work_phone)
values (2, 'CtrogE', '$2y$12$cWWU.g2PIX8rrqxb4Jybr.N5.57dnwbiCU5CFIbQspce6yT/25brC', '1998-05-13', 'Roge', 'Ctrannik',
        'ctrannik555@gmail.com', '+375333236700', 1, '2021-06-24', 'vk.com', null, null, '+375333236700');

-- password: 54862
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`,
                    registration_date, website, about_yourself, job_title, work_phone)
values (3, 'Roma666', '$2y$12$nA5jedTlkTE03eovTVVWxeQ0YLB1195XI6Hg/dlcu0bsth/mAHA5C', '1999-03-18', 'Romann', 'Ranshov',
        'sportzman@gmail.com', '+375293486999', 2, '2021-06-28', 'linkedin.com/rmn18', 'msmc', 'IP',
        '+375335485698');

-- password: sava997
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`,
                    registration_date, website, about_yourself, job_title, work_phone)
values (4, '$a$ha', '$2a$12$uxRobGb9v7W4CHR9JEK6RuH9rLUTr34kNLNAFoCumyMBNt79nJOji', '1997-01-29', 'Sasha', 'Vasilev',
        'cania@gmail.com', '+375338996999', 1, '2021-07-14', null, null, 'student',
        null);

-- password: 333kazanov11
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`,
                    registration_date, website, about_yourself, job_title, work_phone)
values (5, 'Stego', '$2a$12$.sR8QhX.cPvV7dsR8NLIsOMB0Ln4QL09dMw4p5lb032A15UdQv1Gi', '2003-09-24', 'Stepa', 'Ragozin',
        'Stego2003@gmail.com', '+375295451529', 2, '2021-07-28', null, null, null,
        null);

-- password: 64654564rererer
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`,
                    registration_date, website, about_yourself, job_title, work_phone)
values (6, 'runsha', '$2a$12$/ZnK.NUXvpoJd20DXfMMyOh13fVlNrGnA7oYl1rwFNzEZ7a4uBNcK', '2000-02-01', 'Dasha', 'Ranshova',
        'daria0201@gmail.com', '+375293532919', 1, '2021-07-28', null, null, null,
        null);

-- password: f345t54tg433r
insert into `user` (id, login, `password`, date_birth, first_name, last_name, email, phone, `role`,
                    registration_date, website, about_yourself, job_title, work_phone)
values (7, 'zagadka111', '$2a$12$BcvBwZFRobbeU7.FH/8EtOYvEfzxKDROyjt06OhE64VA4aAXSjcWS', '1995-12-11', 'Natasha',
        'Zaiceva',
        'moda225@gmail.com', '+375336698291', 2, '2021-08-01', 'vk.com/lamodaby', null, 'model',
        '+375335569638');

insert into profile_comment (id, profile_owner_id, user_id, `date`, comment_txt)
values (1, 1, 2, '2021-07-05 11:23:50.123', 'first comment');
insert into profile_comment (id, profile_owner_id, user_id, `date`, comment_txt)
values (2, 2, 3, '2021-07-05 20:34:47', 'second comment');
insert into profile_comment (id, profile_owner_id, user_id, `date`, comment_txt)
values (3, 6, 4, '2021-08-05 08:02:33', '3 comment');
insert into profile_comment (id, profile_owner_id, user_id, `date`, comment_txt)
values (4, 6, 5, '2021-08-07 21:06:42', '4 comment');
insert into profile_comment (id, profile_owner_id, user_id, `date`, comment_txt)
values (5, 5, 5, '2021-08-28 16:22:11', '5 comment');

insert into user_event (id, user_id, `name`, `description`, `date`)
values (1, 3, 'birthday', 'admin`s birthday', '2022-05-26 10:00:00');
insert into user_event (id, user_id, `name`, `description`, `date`)
values (2, 1, 'birthday', 'My birthday', '2022-05-26 09:00:00');
insert into user_event (id, user_id, `name`, `description`, `date`)
values (3, 1, 'birthday', 'interview', '2021-09-13 10:30:00');
insert into user_event (id, user_id, `name`, `description`, `date`)
values (4, 5, 'birthday party', 'admin`s birthday', '2022-05-26 16:20:00');
insert into user_event (id, user_id, `name`, `description`, `date`)
values (5, 6, 'dentist', 'take jaw x-ray', '2021-09-18 13:00:00');

insert into role_list (id, name)
values (1, 'Friend');
insert into role_list (id, name)
values (2, 'Best Friend');
insert into role_list (id, name)
values (3, 'Workmate');
insert into role_list (id, name)
values (4, 'Director');
insert into role_list (id, name)
values (5, 'Me ^_^');

insert into contact (id, creator_id, mate_id, date_connected, contact_level, creator_role, mate_role)
values (1, 2, 1, '2021-06-25', true, 5, 5);
insert into contact (id, creator_id, mate_id, date_connected, contact_level, creator_role, mate_role)
values (2, 3, 1, '2021-07-17', false, null, null);
insert into contact (id, creator_id, mate_id, date_connected, contact_level, creator_role, mate_role)
values (3, 7, 6, '2021-08-17', true, 2, 1);
insert into contact (id, creator_id, mate_id, date_connected, contact_level, creator_role, mate_role)
values (4, 5, 6, '2021-08-19', true, 3, 1);


insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (1, 1, 2, '2021-06-25 17:45:31', 'hai');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (2, 2, 1, '2021-06-25 17:47:27', 'hello');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (3, 2, 1, '2021-06-25 17:47:51', 'how are you?');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (4, 1, 2, '2021-06-25 17:48:27', 'shaking like hell on interview...');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (5, 6, 7, '2021-08-11 13:57:33', 'ech');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (6, 7, 6, '2021-08-11 13:57:51', 'ni');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (7, 7, 6, '2021-08-11 13:58:27', 'san');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (8, 7, 6, '2021-08-11 13:57:33', 'chi');
insert into message (id, sender_id, receiver_id, date_created, message_txt)
values (9, 6, 7, '2021-08-11 13:58:11', 'arigato');


insert into community (id, creator_id, `name`, description, date_created)
values (1, 1, 'admin`s home', 'description.....', '2021-06-25');
insert into community (id, creator_id, `name`, description, date_created)
values (2, 2, 'free community', 'description.....', '2021-07-10');
insert into community (id, creator_id, `name`, description, date_created)
values (3, 6, 'Rand_org', 'who read it?!', '2021-08-01');
insert into community (id, creator_id, `name`, description, date_created)
values (4, 4, 'FilmLover', 'say no to serials', '2021-07-20');

insert into user_of_community (id, community_id, user_id, date_entered)
values (1, 1, 1, '2021-06-25');
insert into user_of_community (id, community_id, user_id, date_entered)
values (2, 1, 2, '2021-06-25');
insert into user_of_community (id, community_id, user_id, date_entered)
values (3, 3, 1, '2021-08-01');
insert into user_of_community (id, community_id, user_id, date_entered)
values (4, 3, 3, '2021-08-01');
insert into user_of_community (id, community_id, user_id, date_entered)
values (5, 3, 4, '2021-08-01');
insert into user_of_community (id, community_id, user_id, date_entered)
values (6, 3, 5, '2021-08-01');
insert into user_of_community (id, community_id, user_id, date_entered)
values (7, 3, 7, '2021-08-01');
insert into user_of_community (id, community_id, user_id, date_entered)
values (8, 4, 2, '2021-07-20');
insert into user_of_community (id, community_id, user_id, date_entered)
values (9, 4, 5, '2021-08-29');
insert into user_of_community (id, community_id, user_id, date_entered)
values (10, 4, 3, '2021-08-29');

insert into community_message (id, creator, community_id, `date`, txt)
values (1, 1, 1, '2021-06-25 17:58:31', 'don`t know what to write...');
insert into community_message (id, creator, community_id, `date`, txt)
values (2, 2, 1, '2021-06-25 17:59:27', 'same, dude');
insert into community_message (id, creator, community_id, `date`, txt)
values (3, 2, 1, '2021-06-25 17:59:51', 'talk with yourself leads to mental problems');
insert into community_message (id, creator, community_id, `date`, txt)
values (4, 2, 4, '2021-08-23 13:34:31', 'tell pls what your favourite film');
insert into community_message (id, creator, community_id, `date`, txt)
values (5, 4, 4, '2021-08-23 17:59:11', 'Tron: Legacy');
insert into community_message (id, creator, community_id, `date`, txt)
values (6, 5, 4, '2021-08-25 12:01:40', 'omg really');
insert into community_message (id, creator, community_id, `date`, txt)
values (7, 4, 4, '2021-08-25 17:58:43', 'some better then matrix ;)');
insert into community_message (id, creator, community_id, `date`, txt)
values (8, 3, 3, '2021-08-25 09:09:25', 'wtf');
insert into community_message (id, creator, community_id, `date`, txt)
values (9, 7, 3, '2021-08-25 14:12:13', 'a why we there?');
insert into community_message (id, creator, community_id, `date`, txt)
values (10, 5, 3, '2021-08-25 15:23:46', 'who you are???');
insert into community_message (id, creator, community_id, `date`, txt)
values (11, 4, 3, '2021-08-25 15:24:21', 'your comm mates;D');
insert into community_message (id, creator, community_id, `date`, txt)
values (12, 5, 3, '2021-08-25 15:25:12', '-_-');

insert into blocklist (id, community_id, who_baned, whom_baned, block_date, block_cause)
values (1, 4, 1, 3, '2021-07-01', 'text Y.A.N.G.I!!!!!');
insert into blocklist (id, community_id, who_baned, whom_baned, block_date, block_cause)
values (2, 3, 6, 3, '2021-07-02', 'hate 100');
insert into blocklist (id, community_id, who_baned, whom_baned, block_date, block_cause)
values (3, 3, 6, 1, '2021-07-03', 'love Titanic');
