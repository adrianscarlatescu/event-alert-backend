CREATE TABLE type
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    category_id BIGINT                NOT NULL,
    code        VARCHAR(50)           NOT NULL UNIQUE,
    label       VARCHAR(50)           NOT NULL,
    image_path  VARCHAR(1000)         NOT NULL,
    CONSTRAINT pk_type PRIMARY KEY (id)
);
