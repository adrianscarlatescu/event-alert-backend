CREATE TABLE event
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime              NOT NULL,
    modified_at   datetime              NULL,
    latitude      DOUBLE                NOT NULL,
    longitude     DOUBLE                NOT NULL,
    impact_radius DECIMAL(6, 2)         NULL,
    image_path    VARCHAR(1000)         NOT NULL,
    description   VARCHAR(1000)         NULL,
    severity_id   VARCHAR(50)           NOT NULL,
    type_id       VARCHAR(50)           NOT NULL,
    status_id     VARCHAR(50)           NOT NULL,
    user_id       BIGINT                NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

ALTER TABLE event
    ADD CONSTRAINT ck_event_min_radius CHECK (impact_radius >= 0.00);

ALTER TABLE event
    ADD CONSTRAINT ck_event_max_radius CHECK (impact_radius <= 1000.00);

ALTER TABLE event
    ADD CONSTRAINT fk_event_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE event
    ADD CONSTRAINT fk_event_on_ref_severity FOREIGN KEY (severity_id) REFERENCES ref_severity (id);

ALTER TABLE event
    ADD CONSTRAINT fk_event_on_ref_type FOREIGN KEY (type_id) REFERENCES ref_type (id);

ALTER TABLE event
    ADD CONSTRAINT fk_event_on_ref_status FOREIGN KEY (status_id) REFERENCES ref_status (id);
