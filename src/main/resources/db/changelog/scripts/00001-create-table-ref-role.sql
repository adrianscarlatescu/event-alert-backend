CREATE TABLE ref_role
(
    id          VARCHAR(50)     NOT NULL,
    label       VARCHAR(50)     NOT NULL,
    description VARCHAR(1000)   NOT NULL,
    position    INT             NOT NULL,
    CONSTRAINT pk_ref_role PRIMARY KEY (id)
);

ALTER TABLE ref_role
    ADD CONSTRAINT uc_ref_role_position UNIQUE (position);

ALTER TABLE ref_role
    ADD CONSTRAINT ck_ref_role_min_position CHECK (position >= 1);
