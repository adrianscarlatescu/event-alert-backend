CREATE TABLE ref_severity
(
    id          VARCHAR(50)   NOT NULL,
    label       VARCHAR(50)   NOT NULL,
    color       CHAR(7)       NOT NULL,
    position    INT           NOT NULL,
    CONSTRAINT pk_ref_severity PRIMARY KEY (id)
);

ALTER TABLE ref_severity
    ADD CONSTRAINT uc_ref_severity_position UNIQUE (position);

ALTER TABLE ref_severity
    ADD CONSTRAINT ck_ref_severity_min_position CHECK (position >= 1);
