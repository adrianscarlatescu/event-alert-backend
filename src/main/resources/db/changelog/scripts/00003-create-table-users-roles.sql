CREATE TABLE users_roles
(
    user_id     BIGINT      NOT NULL,
    role_id     VARCHAR(50) NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_on_ref_role FOREIGN KEY (role_id) REFERENCES ref_role (id);
