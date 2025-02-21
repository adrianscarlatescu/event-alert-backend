CREATE TABLE status
(
    id            VARCHAR(50)   NOT NULL,
    label         VARCHAR(50)   NOT NULL,
    color         INT           NOT NULL,
    description   VARCHAR(1000) NOT NULL,
    position      INT           NOT NULL,
    CONSTRAINT pk_status PRIMARY KEY (id)
);

ALTER TABLE status
    ADD CONSTRAINT uc_status_position UNIQUE (position);

ALTER TABLE status
    ADD CONSTRAINT ck_status_min_position CHECK (position >= 1);
