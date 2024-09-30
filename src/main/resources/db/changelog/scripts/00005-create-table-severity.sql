CREATE TABLE severity
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(40)           NULL,
    color INT                   NULL,
    CONSTRAINT pk_severity PRIMARY KEY (id)
);
