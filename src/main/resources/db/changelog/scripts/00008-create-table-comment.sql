CREATE TABLE comment
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    created_at  DATETIME              NOT NULL,
    comment     VARCHAR(1000)         NOT NULL,
    event_id    BIGINT                NOT NULL,
    user_id     BIGINT                NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_on_event FOREIGN KEY (event_id) REFERENCES event (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_on_user FOREIGN KEY (user_id) REFERENCES user (id);
