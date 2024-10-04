CREATE TABLE tag
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(50)           NOT NULL,
    image_path VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id)
);
