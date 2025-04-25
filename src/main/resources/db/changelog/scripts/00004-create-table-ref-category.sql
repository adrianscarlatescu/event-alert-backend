CREATE TABLE ref_category
(
    id         VARCHAR(50)      NOT NULL,
    label      VARCHAR(50)      NOT NULL,
    position   INT              NOT NULL,
    CONSTRAINT pk_ref_category PRIMARY KEY (id)
);

ALTER TABLE ref_category
    ADD CONSTRAINT uc_ref_category_position UNIQUE (position);

ALTER TABLE ref_category
    ADD CONSTRAINT ck_ref_category_min_position CHECK (position >= 1);
