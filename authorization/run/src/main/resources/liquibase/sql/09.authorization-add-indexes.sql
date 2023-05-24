CREATE INDEX ON authorizations.role_user(user_id);
CREATE INDEX ON authorizations.role_user(role_id);
CREATE INDEX ON authorizations.role_authority(role_id);
CREATE INDEX ON authorizations.role_authority(authority_id);