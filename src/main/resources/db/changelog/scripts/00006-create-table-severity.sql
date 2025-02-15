CREATE TABLE severity
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    code  VARCHAR(50)           NOT NULL UNIQUE,
    label VARCHAR(50)           NOT NULL,
    color INT                   NOT NULL,
    CONSTRAINT pk_severity PRIMARY KEY (id)
);
