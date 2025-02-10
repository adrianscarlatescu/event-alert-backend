CREATE TABLE user_role
(
    id          BIGINT AUTO_INCREMENT   NOT NULL,
    name        VARCHAR(50)             NOT NULL,
    description VARCHAR(50)             NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (id)
);
