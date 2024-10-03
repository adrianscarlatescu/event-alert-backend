CREATE TABLE comment
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    date_time datetime              NULL,
    comment   VARCHAR(1000)         NOT NULL,
    event_id  BIGINT                NOT NULL,
    user_id   BIGINT                NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_EVENT FOREIGN KEY (event_id) REFERENCES event (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
