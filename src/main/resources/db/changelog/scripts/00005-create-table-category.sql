CREATE TABLE category
(
    id         VARCHAR(50)      NOT NULL,
    label      VARCHAR(50)      NOT NULL,
    image_path VARCHAR(1000)    NOT NULL,
    position   INT              NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

ALTER TABLE category
    ADD CONSTRAINT uc_category_position UNIQUE (position);

ALTER TABLE category
    ADD CONSTRAINT ck_category_min_position CHECK (position >= 1);
