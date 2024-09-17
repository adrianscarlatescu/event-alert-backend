CREATE TABLE user
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    join_date_time datetime              NULL,
    email          VARCHAR(255)          NULL,
    password       VARCHAR(255)          NULL,
    first_name     VARCHAR(255)          NULL,
    last_name      VARCHAR(255)          NULL,
    date_of_birth  date                  NULL,
    phone_number   VARCHAR(255)          NULL,
    image_path     VARCHAR(255)          NULL,
    gender         VARCHAR(255)          NULL,
    reports_number INT                   NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ALTER reports_number SET DEFAULT 0;

CREATE INDEX idx_email ON user (email);