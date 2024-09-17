CREATE TABLE subscription
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    user_id      BIGINT                NOT NULL,
    latitude     DOUBLE                NULL,
    longitude    DOUBLE                NULL,
    radius       INT                   NULL,
    device_token VARCHAR(255)          NULL,
    CONSTRAINT pk_subscription PRIMARY KEY (id)
);

ALTER TABLE subscription
    ADD CONSTRAINT FK_SUBSCRIPTION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
