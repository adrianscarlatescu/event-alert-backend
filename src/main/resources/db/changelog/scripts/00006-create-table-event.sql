CREATE TABLE event
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  DATETIME              NOT NULL,
    latitude    DOUBLE                NOT NULL,
    longitude   DOUBLE                NOT NULL,
    image_path  VARCHAR(255)          NOT NULL,
    description VARCHAR(1000)         NULL,
    severity_id BIGINT                NOT NULL,
    tag_id      BIGINT                NOT NULL,
    user_id     BIGINT                NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

ALTER TABLE event
    ADD CONSTRAINT fk_event_on_event_severity FOREIGN KEY (severity_id) REFERENCES event_severity (id);

ALTER TABLE event
    ADD CONSTRAINT fk_event_on_event_tag FOREIGN KEY (tag_id) REFERENCES event_tag (id);

ALTER TABLE event
    ADD CONSTRAINT fk_event_on_user FOREIGN KEY (user_id) REFERENCES user (id);
