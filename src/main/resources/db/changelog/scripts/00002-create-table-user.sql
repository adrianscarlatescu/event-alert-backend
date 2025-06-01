CREATE TABLE user
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    joined_at       DATETIME              NOT NULL,
    modified_at     DATETIME              NULL,
    email           VARCHAR(50)           NOT NULL,
    password        VARCHAR(1000)         NULL,
    first_name      VARCHAR(50)           NULL,
    last_name       VARCHAR(50)           NULL,
    date_of_birth   DATE                  NULL,
    phone_number    VARCHAR(15)           NULL,
    image_path      VARCHAR(1000)         NULL,
    reports_number  INT                   NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

CREATE INDEX idx_email ON user (email);

ALTER TABLE user
    ALTER reports_number SET DEFAULT 0;
