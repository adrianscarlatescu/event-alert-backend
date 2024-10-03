CREATE TABLE event
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    date_time     datetime              NULL,
    latitude      DOUBLE                NOT NULL,
    longitude     DOUBLE                NOT NULL,
    image_path    VARCHAR(255)           NOT NULL,
    `description` VARCHAR(1000)         NULL,
    severity_id   BIGINT                NOT NULL,
    tag_id        BIGINT                NOT NULL,
    user_id       BIGINT                NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_SEVERITY FOREIGN KEY (severity_id) REFERENCES severity (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
