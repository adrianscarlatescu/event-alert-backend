CREATE TABLE subscription
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     DATETIME              NOT NULL,
    modified_at    DATETIME              NULL,
    user_id        BIGINT                NOT NULL,
    latitude       DOUBLE                NOT NULL,
    longitude      DOUBLE                NOT NULL,
    radius         INT                   NOT NULL,
    device_id      VARCHAR(1000)         NOT NULL,
    firebase_token VARCHAR(1000)         NOT NULL,
    is_active      BIT(1)                NOT NULL,
    CONSTRAINT pk_subscription PRIMARY KEY (id)
);

ALTER TABLE subscription
    ADD CONSTRAINT check_range_min_radius CHECK (radius >= 1);

ALTER TABLE subscription
    ADD CONSTRAINT check_range_min_radius CHECK (radius <= 10000);

ALTER TABLE subscription
    ADD CONSTRAINT fk_subscription_on_user FOREIGN KEY (user_id) REFERENCES user (id);
