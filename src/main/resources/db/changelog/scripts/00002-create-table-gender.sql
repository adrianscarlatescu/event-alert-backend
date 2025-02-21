CREATE TABLE gender
(
    id          VARCHAR(50) NOT NULL,
    label       VARCHAR(50) NOT NULL,
    position    INT         NOT NULL,
    CONSTRAINT pk_gender PRIMARY KEY (id)
);

ALTER TABLE gender
    ADD CONSTRAINT uc_gender_position UNIQUE (position);

ALTER TABLE gender
    ADD CONSTRAINT ck_gender_min_position CHECK (position >= 1);
