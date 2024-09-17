CREATE TABLE tag
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(40)           NULL,
    image_path VARCHAR(255)          NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id)
);
