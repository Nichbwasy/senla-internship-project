ALTER TABLE authorizations.role_user ADD FOREIGN KEY (role_id) REFERENCES authorizations.role (id) ON DELETE CASCADE;

ALTER TABLE authorizations.role_user ADD FOREIGN KEY (user_id) REFERENCES authorizations.userdata (id) ON DELETE CASCADE;

ALTER TABLE authorizations.role_authority ADD FOREIGN KEY (role_id) REFERENCES authorizations.role (id) ON DELETE CASCADE;

ALTER TABLE authorizations.role_authority ADD FOREIGN KEY (authority_id) REFERENCES authorizations.authority (id) ON DELETE CASCADE;
