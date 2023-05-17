CREATE OR REPLACE FUNCTION remove_car_refund()
	RETURNS TRIGGER AS $remove_car_refund$
BEGIN
	DELETE FROM rental.refundcompensation WHERE id=OLD.refundcompensation_id;
	RETURN NULL;
END;
$remove_car_refund$ LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION remove_request()
	RETURNS TRIGGER AS $remove_request$
BEGIN
	DELETE FROM rental.requestrejection WHERE id=OLD.requestrejection_id;
	DELETE FROM rental.carrefund WHERE id=OLD.carrefund_id;
	RETURN NULL;
END;
$remove_request$ LANGUAGE PLPGSQL;
	
CREATE OR REPLACE TRIGGER remove_car_refund AFTER DELETE ON rental.carrefund
	FOR EACH ROW EXECUTE PROCEDURE remove_car_refund();
	
CREATE OR REPLACE TRIGGER remove_request AFTER DELETE ON rental.request
	FOR EACH ROW EXECUTE PROCEDURE remove_request();