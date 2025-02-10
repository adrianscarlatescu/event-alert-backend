insert into user_role(id, name, description)
values (1, 'BASIC', 'Default user');
insert into user_role(id, name, description)
values (2, 'ADMIN', 'High privileged user');

insert into user(id, email, first_name, last_name, date_of_birth, gender, image_path, phone_number, joined_at)
values (1, "user1@test.com" , "Alan", "Walter", "1984-05-23", "MALE", "img/user_1.jpg", "+03442777999", "2020-03-15 11:30:00");
insert into user(id, email, first_name, last_name, date_of_birth, gender, image_path, phone_number, joined_at)
values (2, "user2@test.com", "John", "Smith", "1987-10-16", "MALE", "img/user_2.jpg", "+44627779991", "2020-03-15 11:30:00");
insert into user(id, email, first_name, last_name, date_of_birth, gender, image_path, phone_number, joined_at)
values (3, "user3@test.com", "Cindy", "Milner", "1990-09-27", "FEMALE", "img/user_3.jpg", "+44848992441", "2020-03-15 11:30:00");
insert into user(id, email, first_name, last_name, date_of_birth, gender, image_path, phone_number, joined_at)
values (4, "user4@test.com", "Andrew", "Carter", "1992-02-14", "MALE", "img/user_4.jpg", "+44226812612", "2020-03-15 11:30:00");
insert into user(id, email, first_name, last_name, date_of_birth, gender, image_path, phone_number, joined_at)
values (5, "user5@test.com", "Amy", "Patterson", "1991-06-12", "FEMALE", "img/user_5.jpg", "+44226812555", "2020-03-15 11:30:00");

insert into users_roles(user_id, role_id)
values (1, 1);
insert into users_roles(user_id, role_id)
values (1, 2);
insert into users_roles(user_id, role_id)
values (2, 1);
insert into users_roles(user_id, role_id)
values (3, 1);
insert into users_roles(user_id, role_id)
values (4, 1);
insert into users_roles(user_id, role_id)
values (5, 1);

insert into event_tag(id, name, image_path)
values (1, "Other", "img/tag_other.png");
insert into event_tag(id, name, image_path)
values (2, "Fire", "img/tag_fire.png");
insert into event_tag(id, name, image_path)
values (3, "Flood", "img/tag_flood.png");
insert into event_tag(id, name, image_path)
values (4, "Earthquake", "img/tag_earthquake.png");
insert into event_tag(id, name, image_path)
values (5, "Landslide", "img/tag_landslide.png");
insert into event_tag(id, name, image_path)
values (6, "Storm", "img/tag_storm.png");
insert into event_tag(id, name, image_path)
values (7, "Heavy rain", "img/tag_heavy_rain.png");
insert into event_tag(id, name, image_path)
values (8, "Snow abundance", "img/tag_intense_snow.png");
insert into event_tag(id, name, image_path)
values (9, "Hurricane", "img/tag_hurricane.png");
insert into event_tag(id, name, image_path)
values (10, "Volcanic eruption", "img/tag_volcanic_erruption.png");
insert into event_tag(id, name, image_path)
values (11, "Tsunami", "img/tag_tsunami.png");
insert into event_tag(id, name, image_path)
values (12, "Dense fog", "img/tag_fog.png");
insert into event_tag(id, name, image_path)
values (13, "Avalanche", "img/tag_avalanche.png");
insert into event_tag(id, name, image_path)
values (14, "Ground hole", "img/tag_ground_hole.png");
insert into event_tag(id, name, image_path)
values (15, "High temperature", "img/tag_high_temperature.png");
insert into event_tag(id, name, image_path)
values (16, "Low temperature", "img/tag_low_temperature.png");
insert into event_tag(id, name, image_path)
values (17, "Fight", "img/tag_fight.png");
insert into event_tag(id, name, image_path)
values (18, "Murder", "img/tag_murder.png");
insert into event_tag(id, name, image_path)
values (19, "Protest", "img/tag_protest.png");
insert into event_tag(id, name, image_path)
values (20, "Traffic accident", "img/tag_traffic_accident.png");
insert into event_tag(id, name, image_path)
values (21, "Industrial accident", "img/tag_industrial_accident.png");
insert into event_tag(id, name, image_path)
values (22, "Dangerous animal", "img/tag_dangerous_animal.png");
insert into event_tag(id, name, image_path)
values (23, "Explosion", "img/tag_explosion.png");
insert into event_tag(id, name, image_path)
values (24, "Dangerous building", "img/tag_dangerous_building.png");
insert into event_tag(id, name, image_path)
values (25, "Electrical shock", "img/tag_electrical_shock.png");
insert into event_tag(id, name, image_path)
values (26, "Intense pollution", "img/tag_intense_pollution.png");
insert into event_tag(id, name, image_path)
values (27, "Blocked road", "img/tag_blocked_road.png");
insert into event_tag(id, name, image_path)
values (28, "Vandalism", "img/tag_vandalism.png");
insert into event_tag(id, name, image_path)
values (29, "Nuclear radiation", "img/tag_nuclear_radiation.png");
insert into event_tag(id, name, image_path)
values (30, "Gunfire", "img/tag_gunfire.png");

insert into event_severity(id, name, color)
values (1, "Critical", 16711680);
insert into event_severity(id, name, color)
values (2, "Major", 16745779);
insert into event_severity(id, name, color)
values (3, "Minor", 16762980);
insert into event_severity(id, name, color)
values (4, "Trivial", 11192420);

insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (1, "2020-04-10 09:10:00", "img/event_1.jpg", 44.3820459, 26.171588, 1, 3, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (2, "2020-04-10 13:11:16", "img/event_2.jpg", 44.4073963, 26.1265611, 1, 2, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (3, "2020-04-10 14:23:55", "img/event_3.jpg", 44.3196758, 27.2105093, 1, 6, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (4, "2020-04-10 17:47:31", "img/event_4.jpg", 28.212882, 83.9754403, 1, 4, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (5, "2020-04-11 03:13:27", "img/event_5.jpg", 47.8032022, 22.8595308, 3, 20, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (6, "2020-04-11 04:24:14", "img/event_6.jpg", 41.1717768, -8.6875015, 3, 3, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (7, "2020-04-12 08:44:27", "img/event_7.jpg", 59.6081696, 16.5511619, 3, 8, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (8, "2020-04-12 14:52:31", "img/event_8.jpg", 51.4927285, -0.2003438, 4, 12, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (9, "2020-04-13 11:11:55", "img/event_9.jpg", 50.9505312, 28.6475922, 4, 16, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (10, "2020-04-13 12:12:42", "img/event_10.jpg", 45.3410878, 25.5164494, 2, 13, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (11, "2020-04-13 14:30:55", "img/event_11.jpg", 37.7510042, 14.9846801, 3, 10, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (12, "2020-04-13 21:00:31", "img/event_12.jpg", 44.4536997, 26.1206009, 4, 14, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (13, "2020-04-14 16:01:16", "img/event_13.jpg", 44.4287873, 26.1038463, 1, 26, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (14, "2020-04-14 16:04:42", "img/event_14.jpg", 44.4345057, 26.0486993, 2, 21, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (15, "2020-04-14 18:22:43", "img/event_15.jpg", 23.2575926, 26.1826986, 4, 16, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (16, "2020-04-15 22:21:43", "img/event_16.jpg", 44.8466356, 24.8753764, 4, 20, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (17, "2020-04-16 04:06:52", "img/event_17.jpg", 44.4526384, 26.0858512, 4, 19, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (18, "2020-04-16 23:42:27", "img/event_18.jpg", 45.7593737, 27.0649587, 2, 4, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (19, "2020-04-18 10:11:55", "img/event_19.jpg", 44.4201438, 26.1000533, 1, 2, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (20, "2020-04-18 14:15:31", "img/event_20.jpg", 44.8671652, 24.8496802, 2, 2, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (21, "2020-04-18 15:17:16", "img/event_21.jpg", 44.84557, 24.8882765, 4, 24, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (22, "2020-04-18 20:18:42", "img/event_22.jpg", 44.4783304, 26.1058571, 4, 12, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (23, "2020-04-23 19:21:27", "img/event_23.jpg", 44.7120456, 26.1712108, 1, 2, 1, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (24, "2020-04-23 22:02:43", "img/event_24.jpg", 44.6685877, 24.8795494, 4, 16, 2, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (25, "2020-04-24 17:01:46", "img/event_25.jpg", 44.3971708, 26.0570636, 2, 17, 2, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (26, "2020-05-01 13:01:22", "img/event_26.jpg", 39.0595653, 21.8750569, 2, 2, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (27, "2020-05-01 14:16:02", "img/event_27.jpg", 57.494195, -4.2122708, 4, 6, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (28, "2020-05-02 14:32:11", "img/event_28.jpg", 50.0596496, 19.925144, 3, 7, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (29, "2020-05-02 15:26:47", "img/event_29.jpg", 52.0487892, 29.2550429, 3, 8, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (30, "2020-05-02 16:10:10", "img/event_30.jpg", 56.946783, 23.6172156, 1, 9, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (31, "2020-05-02 18:52:09", "img/event_31.jpg", 25.2640939, 63.4767481, 1, 1, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (32, "2020-05-03 08:22:09", "img/event_32.jpg", 46.0611305, 8.4043662, 4, 13, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (33, "2020-05-03 10:13:55", "img/event_33.jpg", 51.5148142, -0.0651437, 1, 18, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (34, "2020-05-03 14:02:13", "img/event_34.jpg", 46.6053988, 27.7682106, 1, 18, 3, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (35, "2020-05-03 15:27:25", "img/event_35.jpg", 47.8123253, 13.0818412, 3, 20, 4, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (36, "2020-05-03 16:21:08", "img/event_36.jpg", 50.1123254, 8.6719538, 2, 21, 4, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (37, "2020-05-03 17:12:04", "img/event_37.jpg", 44.7803392, 20.4701967, 3, 23, 4, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (38, "2020-05-04 09:25:54", "img/event_38.jpg", 48.8529932, 2.3499773, 4, 24, 4, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (39, "2020-05-04 11:34:15", "img/event_39.jpg", 41.6451143, -0.8764002, 4, 25, 4, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (40, "2020-05-04 14:01:00", "img/event_40.jpg", 42.6883485, 23.3344281, 2, 26, 4, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (41, "2020-05-04 15:22:18", "img/event_41.jpg", 55.7373716, 37.5287146, 3, 28, 4, null);
insert into event(id, created_at, image_path, latitude, longitude, severity_id, tag_id, user_id, description)
values (42, "2020-05-04 16:00:28", "img/event_42.jpg", 44.3196796, 23.8032826, 4, 28, 4, null);

insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-10 17:15:11", 2, 2, "Is everything fine out there?");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-10 18:44:02", 2, 3,
        "I've seen that the fire passed and only one person got hurt. I will try to get in contact with someone out there.");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-10 19:23:33", 2, 4, "Thanks a lot. Let us know what you've found out.");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-10 20:22:46", 2, 3,
        "It seems that everything is back to normal. The person that got hurt is safe now.");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-10 20:50:17", 2, 1, "The fire has been extinguished and everyone is OK :)");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-12 10:11:52", 6, 3, "Such a bad weather in Portugal... Is everyone OK?");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-12 11:25:02", 6, 1,
        "The water level is not so high. Each person is safe and no one got hurt. I think this bad weather will pass soon. Such a climate change is not usual in the region of Porto.");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-15 12:38:55", 15, 5, "How is it possible to snow in Sahara? Lol.");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-15 14:15:11", 15, 4,
        "Why do you laugh? I think it happened before too. Anyway, it's a unique phenomenon.");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-04-15 14:18:37", 15, 5, "#$@!%*@*$@!#");
insert into event_comment(created_at, event_id, user_id, comment)
values ("2020-05-02 18:15:11", 24, 5, "Snow in April? Lol.");
