CREATE TABLE subscription
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    user_id        BIGINT                NOT NULL,
    latitude       DOUBLE                NOT NULL,
    longitude      DOUBLE                NOT NULL,
    radius         INT                   NOT NULL,
    device_id      VARCHAR(255)          NOT NULL,
    firebase_token VARCHAR(255)          NOT NULL,
    is_active      BIT(1)                NOT NULL,
    CONSTRAINT pk_subscription PRIMARY KEY (id)
);

ALTER TABLE subscription
    ADD CONSTRAINT FK_SUBSCRIPTION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
