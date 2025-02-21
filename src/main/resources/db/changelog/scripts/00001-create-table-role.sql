CREATE TABLE role
(
    id          VARCHAR(50)     NOT NULL,
    label       VARCHAR(50)     NOT NULL,
    description VARCHAR(1000)   NOT NULL,
    position    INT             NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

ALTER TABLE role
    ADD CONSTRAINT uc_role_position UNIQUE (position);

ALTER TABLE role
    ADD CONSTRAINT ck_role_min_position CHECK (position >= 1);
