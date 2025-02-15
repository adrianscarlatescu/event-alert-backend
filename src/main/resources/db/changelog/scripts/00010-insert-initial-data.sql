insert into role
    (id, code, label, description)
values
    (1, 'BASIC', 'Basic user', 'Access to the main features of the application'),
    (2, 'ADMIN', 'Admin user', 'High privileged access to create, modify or delete resources');

insert into user
    (id, email, first_name, last_name, date_of_birth, gender_code, image_path, phone_number, joined_at)
values
    (1, 'user1@test.com' , 'Alan', 'Walter', '1984-05-23', 'MALE', 'img/user_1.jpg', '+03442777999', '2020-03-15 11:30:00'),
    (2, 'user2@test.com', 'John', 'Smith', '1987-10-16', 'MALE', 'img/user_2.jpg', '+44627779991', '2020-03-15 11:30:00'),
    (3, 'user3@test.com', 'Cindy', 'Milner', '1990-09-27', 'FEMALE', 'img/user_3.jpg', '+44848992441', '2020-03-15 11:30:00'),
    (4, 'user4@test.com', 'Andrew', 'Carter', '1992-02-14', 'MALE', 'img/user_4.jpg', '+44226812612', '2020-03-15 11:30:00'),
    (5, 'user5@test.com', 'Amy', 'Patterson', '1991-06-12', 'FEMALE', 'img/user_5.jpg', '+44226812555', '2020-03-15 11:30:00');

insert into users_roles
    (user_id, role_id)
values
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1);

insert into category
    (id, code, label, image_path)
values
    (1, 'HUMAN_MADE', 'Human-made', 'media/event-type-category/category_human_made.png'),
    (2, 'NATURAL', 'Natural', 'media/event-type-category/category_natural.png'),
    (3, 'OTHER', 'Other', 'media/event-type-category/category_other.png');

insert into type
    (id, category_id, code, label, image_path)
values
    (1, 1, 'BLOCKED_ROAD', 'Blocked road', 'media/event-type/human-made/type_blocked_road.png'),
    (2, 1, 'DRUG_ACTIVITY', 'Drug activity', 'media/event-type/human-made/type_drug_activity.png'),
    (3, 1, 'ELECTRICAL_SHOCK', 'Electrical shock', 'media/event-type/human-made/type_electrical_shock.png'),
    (4, 1, 'EXPLOSION', 'Explosion', 'media/event-type/human-made/type_explosion.png'),
    (5, 1, 'FIGHT_ASSAULT', 'Fight/Assault', 'media/event-type/human-made/type_fight_assault.png'),
    (6, 1, 'FIRE', 'Fire', 'media/event-type/human-made/type_fire.png'),
    (7, 1, 'GAS_LEAK', 'Gas leak', 'media/event-type/human-made/type_gas_leak.png'),
    (8, 1, 'GUNFIRE', 'Gunfire', 'media/event-type/human-made/type_gunfire.png'),
    (9, 1, 'INDUSTRIAL_ACCIDENT', 'Industrial accident', 'media/event-type/human-made/type_industrial_accident.png'),
    (10, 1, 'INFRASTRUCTURE_DAMAGE', 'Infrastructure damage', 'media/event-type/human-made/type_infrastructure_damage.png'),
    (11, 1, 'KIDNAPPING', 'Kidnapping', 'media/event-type/human-made/type_kidnapping.png'),
    (12, 1, 'MISSING_PERSON', 'Missing person', 'media/event-type/human-made/type_missing_person.png'),
    (13, 1, 'MURDER_CRIME', 'Murder/Crime', 'media/event-type/human-made/type_murder_crime.png'),
    (14, 1, 'NUCLEAR_RADIATION', 'Nuclear radiation', 'media/event-type/human-made/type_nuclear_radiation.png'),
    (15, 1, 'POLLUTION', 'Pollution', 'media/event-type/human-made/type_pollution.png'),
    (16, 1, 'POWER_OUTAGE', 'Power outage', 'media/event-type/human-made/type_power_outage.png'),
    (17, 1, 'PROTEST_RIOT', 'Protest/Riot', 'media/event-type/human-made/type_protest_riot.png'),
    (18, 1, 'ROBBERY_BURGLARY', 'Robbery/Burglary', 'media/event-type/human-made/type_roberry_burglary.png'),
    (19, 1, 'TERRORIST_ATTACK', 'Terrorist attack', 'media/event-type/human-made/type_terrorist_attack.png'),
    (20, 1, 'TRAFFIC_ACCIDENT', 'Traffic accident', 'media/event-type/human-made/type_traffic_accident.png'),
    (21, 1, 'VANDALISM', 'Vandalism', 'media/event-type/human-made/type_vandalism.png'),

    (22, 2, 'AVALANCHE', 'Avalanche', 'media/event-type/natural/type_avalanche.png'),
    (23, 2, 'DANGEROUS_ANIMAL', 'Dangerous animal', 'media/event-type/natural/type_dangerous_animal.png'),
    (24, 2, 'DROUGHT', 'Drought', 'media/event-type/natural/type_drought.png'),
    (25, 2, 'EARTHQUAKE', 'Earthquake', 'media/event-type/natural/type_earthquake.png'),
    (26, 2, 'FLOOD', 'Flood', 'media/event-type/natural/type_flood.png'),
    (27, 2, 'FOG', 'Fog', 'media/event-type/natural/type_fog.png'),
    (28, 2, 'HIGH_TEMPERATURE', 'High temperature', 'media/event-type/natural/type_high_temperature.png'),
    (29, 2, 'HURRICANE', 'Hurricane', 'media/event-type/natural/type_hurricane.png'),
    (30, 2, 'LANDSLIDE', 'Landslide', 'media/event-type/natural/type_landslide.png'),
    (31, 2, 'LOW_TEMPERATURE', 'Low temperature', 'media/event-type/natural/type_low_temperature.png'),
    (32, 2, 'RAIN', 'Rain', 'media/event-type/natural/type_rain.png'),
    (33, 2, 'SINKHOLE', 'Sinkhole', 'media/event-type/natural/type_sinkhole.png'),
    (34, 2, 'SNOW', 'Snow', 'media/event-type/natural/type_snow.png'),
    (35, 2, 'STORM', 'Storm', 'media/event-type/natural/type_storm.png'),
    (36, 2, 'TSUNAMI', 'Tsunami', 'media/event-type/natural/type_tsunami.png'),
    (37, 2, 'VOLCANIC_ERUPTION', 'Volcanic eruption', 'media/event-type/natural/type_volcanic_eruption.png'),
    (38, 2, 'WILDFIRE', 'Wildfire', 'media/event-type/natural/type_wildfire.png'),

    (39, 3, 'OTHER', 'Other', 'media/event-type/other/type_other.png');

insert into severity
    (id, code, label, color)
values
    (1, 'EXTREME', 'Extreme', 16711680),
    (2, 'MAJOR', 'Major', 16745779),
    (3, 'MINOR', 'Minor', 16762980),
    (4, 'TRIVIAL', 'Trivial', 11192420);

insert into event
    (id, created_at, image_path, latitude, longitude, severity_id, type_id, user_id)
values
    (1, '2020-04-10 09:10:00', 'media/event/event_1.jpg', 44.3820459, 26.171588, 1, 26, 1),
    (2, '2020-04-10 13:11:16', 'media/event/event_2.jpg', 44.4073963, 26.1265611, 1, 6, 1),
    (3, '2020-04-10 14:23:55', 'media/event/event_3.jpg', 44.3196758, 27.2105093, 1, 35, 1),
    (4, '2020-04-10 17:47:31', 'media/event/event_4.jpg', 28.212882, 83.9754403, 1, 25, 1),
    (5, '2020-04-11 03:13:27', 'media/event/event_5.jpg', 47.8032022, 22.8595308, 3, 20, 1),
    (6, '2020-04-11 04:24:14', 'media/event/event_6.jpg', 41.1717768, -8.6875015, 3, 26, 1),
    (7, '2020-04-12 08:44:27', 'media/event/event_7.jpg', 59.6081696, 16.5511619, 3, 34, 1),
    (8, '2020-04-12 14:52:31', 'media/event/event_8.jpg', 51.4927285, -0.2003438, 4, 27, 1),
    (9, '2020-04-13 11:11:55', 'media/event/event_9.jpg', 50.9505312, 28.6475922, 4, 31, 1),
    (10, '2020-04-13 12:12:42', 'media/event/event_10.jpg', 45.3410878, 25.5164494, 2, 22, 1),
    (11, '2020-04-13 14:30:55', 'media/event/event_11.jpg', 37.7510042, 14.9846801, 3, 37, 1),
    (12, '2020-04-13 21:00:31', 'media/event/event_12.jpg', 44.4536997, 26.1206009, 4, 33, 1),
    (13, '2020-04-14 16:01:16', 'media/event/event_13.jpg', 44.4287873, 26.1038463, 1, 15, 1),
    (14, '2020-04-14 16:04:42', 'media/event/event_14.jpg', 44.4345057, 26.0486993, 2, 9, 1),
    (15, '2020-04-14 18:22:43', 'media/event/event_15.jpg', 23.2575926, 26.1826986, 4, 34, 1),
    (16, '2020-04-15 22:21:43', 'media/event/event_16.jpg', 44.8466356, 24.8753764, 4, 20, 1),
    (17, '2020-04-16 04:06:52', 'media/event/event_17.jpg', 44.4526384, 26.0858512, 4, 17, 1),
    (18, '2020-04-16 23:42:27', 'media/event/event_18.jpg', 45.7593737, 27.0649587, 2, 25, 1),
    (19, '2020-04-18 10:11:55', 'media/event/event_19.jpg', 44.4201438, 26.1000533, 1, 6, 1),
    (20, '2020-04-18 14:15:31', 'media/event/event_20.jpg', 44.8671652, 24.8496802, 2, 6, 1),
    (21, '2020-04-18 15:17:16', 'media/event/event_21.jpg', 44.84557, 24.8882765, 4, 10, 1),
    (22, '2020-04-18 20:18:42', 'media/event/event_22.jpg', 44.4783304, 26.1058571, 4, 31, 1),
    (23, '2020-04-23 19:21:27', 'media/event/event_23.jpg', 44.7120456, 26.1712108, 1, 6, 1),
    (24, '2020-04-23 22:02:43', 'media/event/event_24.jpg', 44.6685877, 24.8795494, 4, 31, 2),
    (25, '2020-04-24 17:01:46', 'media/event/event_25.jpg', 44.3971708, 26.0570636, 2, 5, 2),
    (26, '2020-05-01 13:01:22', 'media/event/event_26.jpg', 39.0595653, 21.8750569, 2, 6, 3),
    (27, '2020-05-01 14:16:02', 'media/event/event_27.jpg', 57.494195, -4.2122708, 4, 35, 3),
    (28, '2020-05-02 14:32:11', 'media/event/event_28.jpg', 50.0596496, 19.925144, 3, 32, 3),
    (29, '2020-05-02 15:26:47', 'media/event/event_29.jpg', 52.0487892, 29.2550429, 3, 34, 3),
    (30, '2020-05-02 16:10:10', 'media/event/event_30.jpg', 56.946783, 23.6172156, 1, 32, 3),
    (31, '2020-05-02 18:52:09', 'media/event/event_31.jpg', 25.2640939, 63.4767481, 1, 26, 3),
    (32, '2020-05-03 08:22:09', 'media/event/event_32.jpg', 46.0611305, 8.4043662, 4, 22, 3),
    (33, '2020-05-03 10:13:55', 'media/event/event_33.jpg', 51.5148142, -0.0651437, 1, 13, 3),
    (34, '2020-05-03 14:02:13', 'media/event/event_34.jpg', 46.6053988, 27.7682106, 1, 13, 3),
    (35, '2020-05-03 15:27:25', 'media/event/event_35.jpg', 47.8123253, 13.0818412, 3, 20, 4),
    (36, '2020-05-03 16:21:08', 'media/event/event_36.jpg', 50.1123254, 8.6719538, 2, 15, 4),
    (37, '2020-05-03 17:12:04', 'media/event/event_37.jpg', 44.7803392, 20.4701967, 3, 9, 4),
    (38, '2020-05-04 09:25:54', 'media/event/event_38.jpg', 48.8529932, 2.3499773, 4, 10, 4),
    (39, '2020-05-04 11:34:15', 'media/event/event_39.jpg', 41.6451143, -0.8764002, 4, 14, 4),
    (40, '2020-05-04 14:01:00', 'media/event/event_40.jpg', 42.6883485, 23.3344281, 2, 15, 4),
    (41, '2020-05-04 15:22:18', 'media/event/event_41.jpg', 55.7373716, 37.5287146, 3, 21, 4),
    (42, '2020-05-04 16:00:28', 'media/event/event_42.jpg', 44.3196796, 23.8032826, 4, 21, 4);

insert into comment
    (created_at, event_id, user_id, comment)
values
    ('2020-04-10 17:15:11', 2, 2, 'Is everything fine out there?'),
    ('2020-04-10 18:44:02', 2, 3, 'I''ve seen that the fire passed and only one person got hurt. I will try to get in contact with someone out there.'),
    ('2020-04-10 19:23:33', 2, 4, 'Thanks a lot. Let us know what you''ve found out.'),
    ('2020-04-10 20:22:46', 2, 3, 'It seems that everything is back to normal. The person that got hurt is safe now.'),
    ('2020-04-10 20:50:17', 2, 1, 'The fire has been extinguished and everyone is OK :)'),
    ('2020-04-12 10:11:52', 6, 3, 'Such a bad weather in Portugal... Is everyone OK?'),
    ('2020-04-12 11:25:02', 6, 1, 'The water level is not so high. Each person is safe and no one got hurt. I think this bad weather will pass soon. Such a climate change is not usual in the region of Porto.'),
    ('2020-04-15 12:38:55', 15, 5, 'How is it possible to snow in Sahara? Lol.'),
    ('2020-04-15 14:15:11', 15, 4, 'Why do you laugh? I think it happened before too. Anyway, it''s a unique phenomenon.'),
    ('2020-04-15 14:18:37', 15, 5, '#$@!%*@*$@!#'),
    ('2020-05-02 18:15:11', 24, 5, 'Snow in April? Lol.');
