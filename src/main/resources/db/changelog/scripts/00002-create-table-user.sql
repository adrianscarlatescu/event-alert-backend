CREATE TABLE user
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    joined_at       DATETIME              NULL,
    email           VARCHAR(50)           NULL UNIQUE,
    password        VARCHAR(100)          NULL,
    first_name      VARCHAR(50)           NULL,
    last_name       VARCHAR(50)           NULL,
    date_of_birth   DATE                  NULL,
    phone_number    VARCHAR(20)           NULL,
    image_path      VARCHAR(1000)         NULL,
    gender_code     VARCHAR(50)           NULL,
    reports_number  INT                   NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ALTER reports_number SET DEFAULT 0;

CREATE INDEX idx_email ON user (email);
