CREATE TABLE event_severity
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(50)           NOT NULL,
    color INT                   NOT NULL,
    CONSTRAINT pk_event_severity PRIMARY KEY (id)
);
