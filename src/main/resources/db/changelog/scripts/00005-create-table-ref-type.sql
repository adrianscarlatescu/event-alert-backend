CREATE TABLE ref_type
(
    id              VARCHAR(50)     NOT NULL,
    label           VARCHAR(50)     NOT NULL,
    image_path      VARCHAR(1000)   NOT NULL,
    category_id     VARCHAR(50)     NOT NULL,
    position        INT             NOT NULL,
    CONSTRAINT pk_ref_type PRIMARY KEY (id)
);

ALTER TABLE ref_type
    ADD CONSTRAINT uc_ref_type_position UNIQUE (position);

ALTER TABLE ref_type
    ADD CONSTRAINT ck_ref_type_min_position CHECK (position >= 1);

ALTER TABLE ref_type
    ADD CONSTRAINT fk_ref_type_on_ref_category FOREIGN KEY (category_id) REFERENCES ref_category (id);
