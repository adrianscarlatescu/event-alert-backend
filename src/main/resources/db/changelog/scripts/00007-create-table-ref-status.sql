CREATE TABLE ref_status
(
    id            VARCHAR(50)   NOT NULL,
    label         VARCHAR(50)   NOT NULL,
    color         CHAR(7)       NOT NULL,
    description   VARCHAR(1000) NOT NULL,
    position      INT           NOT NULL,
    CONSTRAINT pk_ref_status PRIMARY KEY (id)
);

ALTER TABLE ref_status
    ADD CONSTRAINT uc_ref_status_position UNIQUE (position);

ALTER TABLE ref_status
    ADD CONSTRAINT ck_ref_status_min_position CHECK (position >= 1);
