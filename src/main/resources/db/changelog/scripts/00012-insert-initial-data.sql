insert into ref_role
(id, label, description, position)
values
    ('ROLE_ADMIN', 'Admin', 'High privileged access to create, modify or delete resources', 1),
    ('ROLE_BASIC', 'Basic', 'Access to the main features of the application', 2);

insert into user
(id, email, first_name, last_name, date_of_birth, phone_number, image_path, joined_at)
values
    (1, 'user1@test.com' , 'Alan', 'Walter', '1984-05-23', '+03442777999', 'media/user/user_1.jpg', '2025-03-15 11:30:00'),
    (2, 'user2@test.com', 'John', 'Smith', '1987-10-16', '+44627779991', 'media/user/user_2.jpg', '2025-03-15 11:30:00'),
    (3, 'user3@test.com', 'Cindy', 'Milner', '1990-09-27', '+44848992441', 'media/user/user_3.jpg', '2025-03-15 11:30:00'),
    (4, 'user4@test.com', 'Andrew', 'Carter', '1992-02-14', '+44226812612', 'media/user/user_4.jpg', '2025-03-15 11:30:00'),
    (5, 'user5@test.com', 'Amy', 'Patterson', '1991-06-12', '+44226812555', 'media/user/user_5.jpg', '2025-03-15 11:30:00');

insert into users_roles
(user_id, role_id)
values
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_BASIC'),
    (2, 'ROLE_BASIC'),
    (3, 'ROLE_BASIC'),
    (4, 'ROLE_BASIC'),
    (5, 'ROLE_BASIC');

insert into ref_category
(id, label, position)
values
    ('HUMAN_MADE', 'Human-made', 1),
    ('NATURAL', 'Natural', 2),
    ('OTHER', 'Other', 3);

insert into ref_type
(id, label, image_path, category_id, position)
values
    ('BLOCKED_ROAD', 'Blocked road', 'media/type/type_blocked_road.png', 'HUMAN_MADE', 1),
    ('DRUG_ACTIVITY', 'Drug activity', 'media/type/type_drug_activity.png', 'HUMAN_MADE', 2),
    ('ELECTRICAL_SHOCK', 'Electrical shock', 'media/type/type_electrical_shock.png', 'HUMAN_MADE', 3),
    ('EXPLOSION', 'Explosion', 'media/type/type_explosion.png', 'HUMAN_MADE', 4),
    ('FIGHT_ASSAULT', 'Fight/Assault', 'media/type/type_fight_assault.png', 'HUMAN_MADE', 5),
    ('FIRE', 'Fire', 'media/type/type_fire.png', 'HUMAN_MADE', 6),
    ('GAS_LEAK', 'Gas leak', 'media/type/type_gas_leak.png', 'HUMAN_MADE', 7),
    ('GUNFIRE', 'Gunfire', 'media/type/type_gunfire.png', 'HUMAN_MADE', 8),
    ('INDUSTRIAL_ACCIDENT', 'Industrial accident', 'media/type/type_industrial_accident.png', 'HUMAN_MADE', 9),
    ('INFRASTRUCTURE_DAMAGE', 'Infrastructure damage', 'media/type/type_infrastructure_damage.png', 'HUMAN_MADE', 10),
    ('KIDNAPPING', 'Kidnapping', 'media/type/type_kidnapping.png', 'HUMAN_MADE', 11),
    ('MISSING_PERSON', 'Missing person', 'media/type/type_missing_person.png', 'HUMAN_MADE', 12),
    ('MURDER_CRIME', 'Murder/Crime', 'media/type/type_murder_crime.png', 'HUMAN_MADE', 13),
    ('NUCLEAR_RADIATION', 'Nuclear radiation', 'media/type/type_nuclear_radiation.png', 'HUMAN_MADE', 14),
    ('POLLUTION', 'Pollution', 'media/type/type_pollution.png', 'HUMAN_MADE', 15),
    ('POWER_OUTAGE', 'Power outage', 'media/type/type_power_outage.png', 'HUMAN_MADE', 16),
    ('PROTEST_RIOT', 'Protest/Riot', 'media/type/type_protest_riot.png', 'HUMAN_MADE', 17),
    ('ROBBERY_BURGLARY', 'Robbery/Burglary', 'media/type/type_robbery_burglary.png', 'HUMAN_MADE', 18),
    ('TERRORIST_ATTACK', 'Terrorist attack', 'media/type/type_terrorist_attack.png', 'HUMAN_MADE', 19),
    ('TRAFFIC_ACCIDENT', 'Traffic accident', 'media/type/type_traffic_accident.png', 'HUMAN_MADE', 20),
    ('VANDALISM', 'Vandalism', 'media/type/type_vandalism.png', 'HUMAN_MADE', 21),

    ('AVALANCHE', 'Avalanche', 'media/type/type_avalanche.png', 'NATURAL', 22),
    ('DANGEROUS_ANIMAL', 'Dangerous animal', 'media/type/type_dangerous_animal.png', 'NATURAL', 23),
    ('DROUGHT', 'Drought', 'media/type/type_drought.png', 'NATURAL', 24),
    ('EARTHQUAKE', 'Earthquake', 'media/type/type_earthquake.png', 'NATURAL', 25),
    ('FLOOD', 'Flood', 'media/type/type_flood.png', 'NATURAL', 26),
    ('FOG', 'Fog', 'media/type/type_fog.png', 'NATURAL', 27),
    ('HIGH_TEMPERATURE', 'High temperature', 'media/type/type_high_temperature.png', 'NATURAL', 28),
    ('HURRICANE', 'Hurricane', 'media/type/type_hurricane.png', 'NATURAL', 29),
    ('LANDSLIDE', 'Landslide', 'media/type/type_landslide.png', 'NATURAL', 30),
    ('LOW_TEMPERATURE', 'Low temperature', 'media/type/type_low_temperature.png', 'NATURAL', 31),
    ('RAIN', 'Rain', 'media/type/type_rain.png', 'NATURAL', 32),
    ('SINKHOLE', 'Sinkhole', 'media/type/type_sinkhole.png', 'NATURAL', 33),
    ('SNOW', 'Snow', 'media/type/type_snow.png', 'NATURAL', 34),
    ('STORM', 'Storm', 'media/type/type_storm.png', 'NATURAL', 35),
    ('TSUNAMI', 'Tsunami', 'media/type/type_tsunami.png', 'NATURAL', 36),
    ('VOLCANIC_ERUPTION', 'Volcanic eruption', 'media/type/type_volcanic_eruption.png', 'NATURAL', 37),
    ('WILDFIRE', 'Wildfire', 'media/type/type_wildfire.png', 'NATURAL', 38),

    ('OTHER', 'Other', 'media/type/type_other.png', 'OTHER', 39);

insert into ref_severity
(id, label, color, position)
values
    ('TRIVIAL', 'Trivial', '#AAC864', 1),
    ('MINOR', 'Minor', '#FFC864', 2),
    ('MAJOR', 'Major', '#FF8533', 3),
    ('EXTREME', 'Extreme', '#FF0000', 4);

insert into ref_status
(id, label, color, description, position)
values
    ('PENDING', 'Pending', '#E3EE00', 'The event has not yet occurred', 1),
    ('ACTIVE_ONGOING', 'Active/Ongoing', '#07E300', 'The event is currently occurring', 2),
    ('RESOLVED', 'Resolved', '#00D1EE', 'Immediate danger has passed', 3),
    ('RECOVERY', 'Recovery', '#AD00EE', 'Efforts are ongoing to recover the effects', 4),
    ('CLOSED', 'Closed', '#FF9900', 'The event is concluded or no longer relevant', 5),
    ('CANCELED', 'Canceled', '#EE3600', 'The event was a false alarm or did not escalate', 6);

insert into event
(id, created_at, image_path, latitude, longitude, impact_radius, type_id, severity_id, status_id, user_id)
values
    (1, '2025-04-10 09:10:00', 'media/event/event_1.jpg', 44.459127, 25.9936983, 1.5, 'FLOOD', 'MAJOR', 'ACTIVE_ONGOING', 1),
    (2, '2025-04-10 21:15:05', 'media/event/event_2.jpg', 44.4157608, 26.0232191, 1, 'FIRE', 'EXTREME', 'RESOLVED', 1),
    (3, '2025-04-10 14:23:55', 'media/event/event_3.jpg', 44.3196758, 27.2105093, 10, 'HURRICANE', 'EXTREME', 'RECOVERY', 3),
    (4, '2025-04-10 17:47:31', 'media/event/event_4.jpg', 28.212882, 83.9754403, 100, 'EARTHQUAKE', 'EXTREME', 'RECOVERY',  4),
    (5, '2025-04-11 03:13:27', 'media/event/event_5.jpg', 47.8032022, 22.8595308, 0, 'TRAFFIC_ACCIDENT', 'MINOR', 'ACTIVE_ONGOING', 1),
    (6, '2025-04-11 04:24:14', 'media/event/event_6.jpg', 41.1717768, -8.6875015, 5, 'FLOOD', 'MINOR', 'ACTIVE_ONGOING', 1),
    (7, '2025-04-12 08:44:27', 'media/event/event_7.jpg', 59.6081696, 16.5511619, 25, 'SNOW', 'MINOR', 'RESOLVED', 2),
    (8, '2025-04-12 14:52:31', 'media/event/event_8.jpg', 51.4927285, -0.2003438, null, 'FOG', 'MINOR', 'CLOSED', 3),
    (9, '2025-04-13 11:11:55', 'media/event/event_9.jpg', 50.9505312, 28.6475922, null, 'LOW_TEMPERATURE', 'TRIVIAL', 'ACTIVE_ONGOING', 2),
    (10, '2025-04-13 12:12:42', 'media/event/event_10.jpg', 45.3410878, 25.5164494, 10, 'AVALANCHE', 'MAJOR', 'PENDING', 1),
    (11, '2025-04-13 14:30:55', 'media/event/event_11.jpg', 37.7510042, 14.9846801, 250, 'VOLCANIC_ERUPTION', 'MAJOR', 'RECOVERY', 4),
    (12, '2025-04-13 21:00:31', 'media/event/event_12.jpg', 44.4536997, 26.1206009, 0.25, 'TRAFFIC_ACCIDENT', 'TRIVIAL', 'RESOLVED', 3),
    (13, '2025-04-14 16:01:16', 'media/event/event_13.jpg', 44.4877646, 26.0469874, 1, 'POLLUTION', 'EXTREME', 'ACTIVE_ONGOING', 3),
    (14, '2025-04-14 16:04:42', 'media/event/event_14.jpg', 44.4345057, 26.0486993, 1.5, 'INDUSTRIAL_ACCIDENT', 'MAJOR', 'ACTIVE_ONGOING', 1),
    (15, '2025-04-14 18:22:43', 'media/event/event_15.jpg', 23.2575926, 26.1826986, null, 'SNOW', 'TRIVIAL', 'CLOSED', 1),
    (16, '2025-04-15 22:21:43', 'media/event/event_16.jpg', 44.8466356, 24.8753764, 0, 'TRAFFIC_ACCIDENT', 'TRIVIAL', 'RESOLVED', 2),
    (17, '2025-04-16 04:06:52', 'media/event/event_17.jpg', 44.4526384, 26.0858512, 1, 'PROTEST_RIOT', 'TRIVIAL', 'ACTIVE_ONGOING', 1),
    (18, '2025-04-16 23:42:27', 'media/event/event_18.jpg', 45.7593737, 27.0649587, 50, 'EARTHQUAKE', 'MAJOR', 'ACTIVE_ONGOING', 4),
    (19, '2025-04-18 10:11:55', 'media/event/event_19.jpg', 44.4201438, 26.1000533, 0.5, 'FIRE', 'EXTREME', 'ACTIVE_ONGOING', 2),
    (20, '2025-04-18 14:15:31', 'media/event/event_20.jpg', 44.8671652, 24.8496802, 1, 'FIRE', 'MAJOR', 'ACTIVE_ONGOING', 1),
    (21, '2025-04-18 15:17:16', 'media/event/event_21.jpg', 44.84557, 24.8882765, null, 'INFRASTRUCTURE_DAMAGE', 'TRIVIAL', 'CLOSED', 1),
    (22, '2025-04-18 20:18:42', 'media/event/event_22.jpg', 44.4783304, 26.1058571, null, 'LOW_TEMPERATURE', 'MINOR', 'CLOSED', 1),
    (23, '2025-04-23 19:21:27', 'media/event/event_23.jpg', 44.7120456, 26.1712108, 5, 'WILDFIRE', 'EXTREME', 'ACTIVE_ONGOING', 1),
    (24, '2025-04-23 22:02:43', 'media/event/event_24.jpg', 44.6685877, 24.8795494, null, 'LOW_TEMPERATURE', 'TRIVIAL', 'CLOSED', 2),
    (25, '2025-04-24 17:01:46', 'media/event/event_25.jpg', 44.3971708, 26.0570636, 0, 'FIGHT_ASSAULT', 'MAJOR', 'ACTIVE_ONGOING', 2),
    (26, '2025-05-01 13:01:22', 'media/event/event_26.jpg', 39.0595653, 21.8750569, 1, 'FIRE', 'MAJOR', 'ACTIVE_ONGOING', 3),
    (27, '2025-05-01 14:16:02', 'media/event/event_27.jpg', 57.494195, -4.2122708, 10, 'STORM', 'TRIVIAL', 'RECOVERY', 3),
    (28, '2025-05-02 14:32:11', 'media/event/event_28.jpg', 50.0596496, 19.925144, null, 'RAIN', 'MINOR', 'CLOSED', 3),
    (29, '2025-05-02 15:26:47', 'media/event/event_29.jpg', 52.0487892, 29.2550429, null, 'SNOW', 'MINOR', 'ACTIVE_ONGOING', 3),
    (30, '2025-05-02 16:10:10', 'media/event/event_30.jpg', 56.946783, 23.6172156, 75, 'STORM', 'EXTREME', 'PENDING', 3),
    (31, '2025-05-02 18:52:09', 'media/event/event_31.jpg', 25.2640939, 63.4767481, 500, 'TSUNAMI', 'EXTREME', 'ACTIVE_ONGOING', 3),
    (32, '2025-05-03 08:22:09', 'media/event/event_32.jpg', 46.0611305, 8.4043662, 5, 'AVALANCHE', 'TRIVIAL', 'CLOSED', 3),
    (33, '2025-05-03 10:13:55', 'media/event/event_33.jpg', 51.5148142, -0.0651437, 0, 'MURDER_CRIME', 'EXTREME', 'RESOLVED', 3),
    (34, '2025-05-03 14:02:13', 'media/event/event_34.jpg', 46.6053988, 27.7682106, 0, 'MURDER_CRIME', 'EXTREME', 'RESOLVED', 3),
    (35, '2025-05-03 15:27:25', 'media/event/event_35.jpg', 47.8123253, 13.0818412, 0, 'TRAFFIC_ACCIDENT', 'MINOR', 'CLOSED', 4),
    (36, '2025-05-03 16:21:08', 'media/event/event_36.jpg', 50.1123254, 8.6719538, 250, 'POLLUTION', 'MAJOR', 'ACTIVE_ONGOING', 4),
    (37, '2025-05-03 17:12:04', 'media/event/event_37.jpg', 44.7803392, 20.4701967, null, 'INDUSTRIAL_ACCIDENT', 'MINOR', 'ACTIVE_ONGOING', 4),
    (38, '2025-05-04 09:25:54', 'media/event/event_38.jpg', 48.8529932, 2.3499773, null, 'INFRASTRUCTURE_DAMAGE', 'TRIVIAL', 'PENDING', 4),
    (39, '2025-05-04 11:34:15', 'media/event/event_39.jpg', 41.6451143, -0.8764002, 1000, 'NUCLEAR_RADIATION', 'MAJOR', 'ACTIVE_ONGOING', 4),
    (40, '2025-05-04 14:01:00', 'media/event/event_40.jpg', 42.6883485, 23.3344281, 125, 'POLLUTION', 'MAJOR', 'ACTIVE_ONGOING', 4),
    (41, '2025-05-04 15:22:18', 'media/event/event_41.jpg', 44.442967, 25.9830953, 0, 'VANDALISM', 'MINOR', 'RECOVERY', 4),
    (42, '2025-05-04 16:00:28', 'media/event/event_42.jpg', 44.3196796, 23.8032826, 0, 'VANDALISM', 'TRIVIAL', 'RECOVERY', 4);

insert into comment
(id, created_at, event_id, user_id, comment)
values
    (1, '2025-04-10 21:17:11', 2, 2, 'Is everything fine out there?'),
    (2, '2025-04-10 21:45:02', 2, 3, 'I''ve seen that the fire passed and only one person got hurt. I will try to get in contact with someone out there.'),
    (3, '2025-04-10 21:50:33', 2, 4, 'Thanks a lot. Let us know what you''ve found out.'),
    (4, '2025-04-10 22:03:46', 2, 3, 'It seems that everything is back to normal. The person that got hurt is safe now.'),
    (5, '2025-04-10 23:34:17', 2, 1, 'The fire has been extinguished and everyone is OK :)'),
    (6, '2025-04-12 10:11:52', 6, 3, 'Such a bad weather in Portugal... Is everyone OK?'),
    (7, '2025-04-12 11:25:02', 6, 1, 'The water level is not so high. Each person is safe and no one got hurt. I think this bad weather will pass soon. Such a climate change is not usual in the region of Porto.'),
    (8, '2025-04-15 12:38:55', 15, 5, 'How is it possible to snow in Sahara? Lol.'),
    (9, '2025-04-15 14:15:11', 15, 4, 'Why do you laugh? I think it happened before too. Anyway, it''s a unique phenomenon.'),
    (10, '2025-04-15 14:18:37', 15, 5, '#$@!%*@*$@!#'),
    (11, '2025-05-02 18:15:11', 24, 5, 'Snow in April? Lol.'),
    (12, '2025-04-10 19:23:33', 4, 2, 'Thank God this app exists.');

insert into ref_order
(id, label, image_path, position)
values
    ('BY_DATE_ASCENDING', 'By date ascending', 'media/order/by_date.png', 1),
    ('BY_DATE_DESCENDING', 'By date descending', 'media/order/by_date.png', 2),
    ('BY_SEVERITY_ASCENDING', 'By severity ascending', 'media/order/by_severity.png', 3),
    ('BY_SEVERITY_DESCENDING', 'By severity descending', 'media/order/by_severity.png', 4),
    ('BY_IMPACT_RADIUS_ASCENDING', 'By impact radius ascending', 'media/order/by_impact_radius.png', 5),
    ('BY_IMPACT_RADIUS_DESCENDING', 'By impact radius descending', 'media/order/by_impact_radius.png', 6),
    ('BY_DISTANCE_ASCENDING', 'By distance ascending', 'media/order/by_distance.png', 7),
    ('BY_DISTANCE_DESCENDING', 'By distance descending', 'media/order/by_distance.png', 8);
