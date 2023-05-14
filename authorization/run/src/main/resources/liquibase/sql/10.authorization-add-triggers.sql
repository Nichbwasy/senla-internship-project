CREATE OR REPLACE FUNCTION remove_user()
	RETURNS TRIGGER AS $remove_user$
BEGIN
	DELETE FROM authorizations.role_user WHERE users_id=OLD.id;
	RETURN NULL;
END;
$remove_user$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION remove_role()
	RETURNS TRIGGER AS $remove_role$
BEGIN
	DELETE FROM authorizations.role_authority WHERE role_id=OLD.id;
	DELETE FROM authorizations.role_user WHERE role_id=OLD.id;
	RETURN NULL;
END;
$remove_role$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION remove_authority()
	RETURNS TRIGGER AS $remove_authority$
BEGIN
	DELETE FROM authorizations.role_authority WHERE authority_id=OLD.id;
	RETURN NULL;
END;
$remove_authority$ LANGUAGE PLPGSQL;
	
CREATE OR REPLACE TRIGGER remove_user AFTER DELETE ON authorizations.userdata
	FOR EACH ROW EXECUTE PROCEDURE remove_user();

CREATE OR REPLACE TRIGGER remove_role AFTER DELETE ON authorizations.role
	FOR EACH ROW EXECUTE PROCEDURE remove_role();

CREATE OR REPLACE TRIGGER remove_authority AFTER DELETE ON authorizations.authority
	FOR EACH ROW EXECUTE PROCEDURE remove_authority();