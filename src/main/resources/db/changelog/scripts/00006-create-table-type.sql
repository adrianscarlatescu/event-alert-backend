CREATE TABLE type
(
    id              VARCHAR(50)     NOT NULL,
    label           VARCHAR(50)     NOT NULL,
    image_path      VARCHAR(1000)   NOT NULL,
    category_id     VARCHAR(50)     NOT NULL,
    position        INT             NOT NULL,
    CONSTRAINT pk_type PRIMARY KEY (id)
);

ALTER TABLE type
    ADD CONSTRAINT uc_type_position UNIQUE (position);

ALTER TABLE type
    ADD CONSTRAINT ck_type_min_position CHECK (position >= 1);

ALTER TABLE type
    ADD CONSTRAINT fk_type_on_category FOREIGN KEY (category_id) REFERENCES category (id);
