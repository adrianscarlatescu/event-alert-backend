CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_on_user_role FOREIGN KEY (role_id) REFERENCES user_role (id);
