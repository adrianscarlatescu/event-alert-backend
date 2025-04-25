CREATE TABLE ref_order
(
    id                      VARCHAR(50)     NOT NULL,
    label                   VARCHAR(50)     NOT NULL,
    image_path              VARCHAR(1000)   NOT NULL,
    position                INT             NOT NULL,
    CONSTRAINT pk_ref_order PRIMARY KEY (id)
);

ALTER TABLE ref_order
    ADD CONSTRAINT uc_ref_order_position UNIQUE (position);

ALTER TABLE ref_order
    ADD CONSTRAINT uc_ref_order_position CHECK (position >= 1);
