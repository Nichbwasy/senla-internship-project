CREATE OR REPLACE FUNCTION remove_car_registration()
	RETURNS TRIGGER AS $remove_car$
BEGIN
	DELETE FROM car.registrations WHERE id=OLD.registration_id;
	RETURN NULL;
END;
$remove_car$ LANGUAGE PLPGSQL;

CREATE OR REPLACE TRIGGER remove_car AFTER DELETE ON car.car
	FOR EACH ROW EXECUTE PROCEDURE remove_car_registration();