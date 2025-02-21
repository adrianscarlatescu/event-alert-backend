CREATE TABLE severity
(
    id          VARCHAR(50)   NOT NULL,
    label       VARCHAR(50)   NOT NULL,
    color       CHAR(7)       NOT NULL,
    position    INT           NOT NULL,
    CONSTRAINT pk_severity PRIMARY KEY (id)
);

ALTER TABLE severity
    ADD CONSTRAINT uc_severity_position UNIQUE (position);

ALTER TABLE severity
    ADD CONSTRAINT ck_severity_min_position CHECK (position >= 1);
