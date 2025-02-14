CREATE TABLE user_role
(
    id          BIGINT AUTO_INCREMENT   NOT NULL,
    code        VARCHAR(50)             NOT NULL UNIQUE,
    label       VARCHAR(50)             NOT NULL,
    description VARCHAR(1000)           NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (id)
);
