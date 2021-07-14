DELETE FROM blocklist;
DELETE FROM community_message;
DELETE FROM user_of_community;
DELETE FROM community;
DELETE FROM message;
DELETE FROM contact;
DELETE FROM role_list;
DELETE FROM user_event;
DELETE FROM profile_comment;
DELETE FROM `user`;
DELETE FROM access_role;

-- insert into user_event values (1, 3, 'birthday', 'admin`s birthday', '2022-05-26');
--
-- insert into role_list values (1, 'Friend');
-- insert into role_list values (2, 'Best Friend');
-- insert into role_list values (3, 'Workmate');
-- insert into role_list values (4, 'Director');
-- insert into role_list values (5, 'Me ^_^');
--
-- insert into contact values (1, 2, 1, '2021-06-25', true, 5);
-- insert into contact values (2, 3, 1, '2021-07-17', false, 3);
--
-- insert into message values (1, 1, 2, '2021-06-25 17:45:31', 'hai');
-- insert into message values (2, 2, 1, '2021-06-25 17:47:27', 'hello');
-- insert into message values (3, 2, 1, '2021-06-25 17:47:51', 'how are you?');
-- insert into message values (4, 1, 2, '2021-06-25 17:48:27', 'shaking like hell on interview...');
-- insert into message values (5, 2, 1, '2021-06-25 17:47:33', '=/');
--
-- insert into community values (1, 1, 'admin`s home', 'description.....', '2021-06-25');
--
-- insert into user_of_community values (1, 1, 1, '2021-06-25');
-- insert into user_of_community values (2, 1, 2, '2021-06-25');
--
-- insert into community_message values (1, 1, 1, '2021-06-25 17:58:31', 'don`t know what to write...');
-- insert into community_message values (2, 2, 1, '2021-06-25 17:59:27', 'same, dude');
-- insert into community_message values (3, 2, 1, '2021-06-25 17:59:51', 'talk with yourself leads to mental problems');
--
-- insert into blocklist values (1, 1, 3, 1, '2021-07-01', 'text U.A.N.G.I!!!!!');
